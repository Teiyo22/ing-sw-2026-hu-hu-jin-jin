package it.polimi.ingsw.controller.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.common.ConnectionMonitor;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyPausedState;
import it.polimi.ingsw.controller.server.lobby.states.LobbyResumableState;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.controller.server.lobby.states.LobbyState;
import it.polimi.ingsw.controller.server.network.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.io.*;
import java.lang.reflect.Type;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ServerController implements VirtualServer {
    private static ServerController instance;

    private NetworkServer networkServer;
    private final ConnectionMonitor connectionMonitor = new ConnectionMonitor();
    private final ExecutorService requestService = Executors.newVirtualThreadPerTaskExecutor();
    private final ExecutorService responseService = Executors.newVirtualThreadPerTaskExecutor();
    private final ScheduledExecutorService persistenceService = Executors.newSingleThreadScheduledExecutor();
    private final Gson gson = new Gson();

    private final AtomicInteger nextLobbyID = new AtomicInteger(1);
    private final Map<Integer, LobbyController> lobbies = new ConcurrentHashMap<>();
    private final Map<Integer, LobbyController> savedLobbies = new ConcurrentHashMap<>();

    private final Map<String, ClientInterface> allClients = new ConcurrentHashMap<>();
    private final Map<String, ClientInterface> playingClients = new ConcurrentHashMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    //=============================================================================
    // Lobby management methods
    //=============================================================================

    @Override
    public void createLobby(String clientID, int playerNum, Totem totem) {
        Logger.getInstance().print(LoggerLevel.SERVER, String.format("Received request [Create Lobby] with [%d Players] from [Client %s]", playerNum, clientID));

        readLock.lock();
        try {
            ClientInterface client = allClients.get(clientID);
            if (client == null) return;

            LobbyController currLobbyController = client.getCurrLobbyController();
            if (currLobbyController != null && currLobbyController.getPlayers().containsKey(client)) {
                client.showError(new ErrorMessage("Lobby Create Error", "You are already in a lobby"));
                return;
            }

            int id = nextLobbyID.getAndIncrement();
            Player player = new Player(clientID, totem);

            LobbyController lobbyController = new LobbyController(id, playerNum);
            lobbyController.add(client, player);
            client.setCurrLobbyController(lobbyController);
            lobbies.put(lobbyController.getID(), lobbyController);

            client.createLobby(lobbyController.getLobby(), player);
            broadcastLobbyAddition(lobbyController.getLobby());
            Logger.getInstance().print(LoggerLevel.SERVER, String.format("Successfully created [Lobby %d] with [%d Players] for [Client %s]", id, playerNum, clientID));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void joinLobby(String clientID, int lobbyID, Totem totem) {
        Logger.getInstance().print(LoggerLevel.SERVER, String.format("Received request [Join] [Lobby %d] from [Client %s]", lobbyID, clientID));

        readLock.lock();
        try {
            ClientInterface client = allClients.get(clientID);
            if (client == null) return;

            LobbyController lobbyController = lobbies.get(lobbyID);
            if (lobbyController != null) {
                Player player = new Player(clientID, totem);
                lobbyController.joinLobby(client, player);
            } else {
                client.showError(new ErrorMessage("Join Lobby Error", "This lobby is not available"));
            }
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void leaveLobby(String clientID, int lobbyID) {
        Logger.getInstance().print(LoggerLevel.SERVER, String.format("Received request [Leave] [Lobby %d] from [Client %s]", lobbyID, clientID));

        writeLock.lock();
        try {
            ClientInterface client = allClients.get(clientID);
            if (client == null) return;

            LobbyController lobbyController = lobbies.get(lobbyID);
            if (lobbyController != null)
                lobbyController.remove(client);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void startLobby(String clientID, int lobbyID) {
        Logger.getInstance().print(LoggerLevel.SERVER, String.format("Received request [Start] [Lobby %d] from [Client %s]", lobbyID, clientID));

        readLock.lock();
        try {
            ClientInterface client = allClients.get(clientID);
            if (client == null) return;

            LobbyController lobbyController = lobbies.get(lobbyID);
            if (lobbyController != null)
                lobbyController.startLobby(client);
            else
                client.showError(new ErrorMessage("Start Lobby Error", "This lobby is not available"));
        } finally {
            readLock.unlock();
        }
    }

    private List<Lobby> getWaitingLobbies() {
        readLock.lock();
        try {
            return this.lobbies.values().stream()
                .filter(LobbyController::isShowable)
                .map(LobbyController::getLobby)
                .toList();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void getLobbyInfo(String clientID, int lobbyID) {
        Logger.getInstance().print(LoggerLevel.SERVER, String.format("Received request [Info] of [Lobby %d] from [Client %s]", lobbyID, clientID));

        readLock.lock();
        try {
            ClientInterface client = allClients.get(clientID);
            if (client == null) return;

            LobbyController lobbyController = lobbies.get(lobbyID);

            if (lobbyController != null)
                lobbyController.getLobbyInfo(client);
            else
                client.showError(new ErrorMessage("Lobby Info Error", "Lobby not found"));
        } finally {
            readLock.unlock();
        }
    }

    public void broadcastLobbyRemoval(int lobbyID) {
        allClients.values().stream()
            .filter(c -> !playingClients.containsKey(c.getID()))
            .forEach(c -> c.removeLobby(lobbyID));
    }

    public void broadcastLobbyAddition(Lobby lobby) {
        allClients.values().stream()
            .filter(c -> !playingClients.containsKey(c.getID()))
            .forEach(c -> c.addLobby(lobby));
    }

    public void broadcastLobbyUpdate(Lobby lobby) {
        allClients.values().stream()
            .filter(c -> !playingClients.containsKey(c.getID()))
            .forEach(c -> c.updateLobby(lobby));
    }

    public void removeLobby(int lobbyID) {
        lobbies.remove(lobbyID);
    }

    //=============================================================================
    // Game interaction methods
    //=============================================================================

    @Override
    public void requestAction(String clientID, int lobbyID, PlayerAction action) {
        Logger.getInstance().print(LoggerLevel.SERVER, String.format("Received [Action] request for [Lobby %d] from [Client %s]", lobbyID, clientID));

        readLock.lock();
        try {
            ClientInterface client = allClients.get(clientID);
            if (client == null) return;

            LobbyController lobby = lobbies.get(lobbyID);
            if (lobby != null)
                lobby.playAction(client, action);
            else
                client.showError(new ErrorMessage("Lobby Action Error", "This lobby is not available"));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void getLeaderboard(String clientID, int playerNum) {

    }

    //=============================================================================
    // Client management methods
    //=============================================================================

    @Override
    public void registerClient(ClientInterface client) {
        String id = UUID.randomUUID().toString();

        allClients.put(id, client);
        connectionMonitor.registerClient(client);

        client.setConnected(true);
        client.setID(id);

        Logger.getInstance().print(LoggerLevel.SERVER, "Client connected with temporary id: " + id);
    }

    @Override
    public void login(String clientID, String username) {
        Logger.getInstance().print(LoggerLevel.SERVER, String.format("Received request to [Login] from [Client %s] as [%s]", clientID, username));

        writeLock.lock();
        try {
            if (!allClients.containsKey(clientID)) return;

            ClientInterface client = allClients.get(clientID);
            if (username.isEmpty()) {
                client.showError(new ErrorMessage("Login Error", "Username cannot be empty"));
                Logger.getInstance().print(LoggerLevel.SERVER, String.format("[Client %s] failed to login as [%s]", clientID, username));
                return;
            }

            ClientInterface previousValue = allClients.putIfAbsent(username, client);
            if (previousValue == null) {
                ClientInterface loggedInClient = allClients.remove(clientID);
                loggedInClient.confirmLogin(username);

                List<Lobby> lobbies = getWaitingLobbies();
                loggedInClient.showWaitingLobbies(lobbies);

                Logger.getInstance().print(LoggerLevel.SERVER, String.format("[Client %s] successfully logged in as [%s]", clientID, username));
            } else {
                client.showError(new ErrorMessage("Login Error", "Username already in use"));
                Logger.getInstance().print(LoggerLevel.SERVER, String.format("[Client %s] failed to login as [%s]", clientID, username));
            }
        } finally {
            writeLock.unlock();
        }

    }

    public void addToPlayingClients(Collection<ClientInterface> clients) {
        for (ClientInterface client : clients)
            playingClients.put(client.getID(), client);
    }

    public void removeFromPlayingClients(Collection<ClientInterface> clients) {
        for (ClientInterface client : clients) {
            playingClients.remove(client.getID());

            List<Lobby> lobbies = getWaitingLobbies();
            client.showWaitingLobbies(lobbies);
        }
    }

    //=============================================================================
    // Persistence
    //=============================================================================

    public void persistLobbies() {
        persistenceService.scheduleAtFixedRate(() -> {
            writeLock.lock();
            try {
                System.out.println("[Persistence] Lobbies totali: " + lobbies.size());
                lobbies.forEach((id, lc) -> System.out.println("[Persistence] Lobby " + id + " stato: " + lc.getState().getClass().getSimpleName()));
                //gets lobby in Running,Paused and Resumable
                Map<Integer, Game> persLobby = lobbies.entrySet().stream()
                    .filter(e -> {
                        LobbyState state = e.getValue().getState();
                        return state instanceof LobbyRunningState || state instanceof LobbyPausedState
                            || state instanceof LobbyResumableState;
                    })
                    //gets the lobbyID as Key and model as value
                    .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().getModel()
                    ));
                System.out.println("[Persistence] Lobby da salvare: " + persLobby.size());
                new File("saves").mkdirs();
                try (FileWriter writer = new FileWriter("saves/lobbies.json")) {
                    gson.toJson(persLobby, writer);
                    writer.flush();
                }
            } catch (IOException e) {
                System.err.println("[Persistence] Errore: " + e.getMessage());
                System.err.println("[Persistence] Path: " + new File("saves").getAbsolutePath());
            } finally {
                writeLock.unlock();
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    public void loadPersistedLobbies() throws FileNotFoundException {
        File file = new File("saves/lobbies.json");
        if (!file.exists()) return;

        try {
            FileReader reader = new FileReader(file);
            Type type = new TypeToken<Map<Integer, Game>>() {
            }.getType();
            Map<Integer, Game> savedMap = gson.fromJson(reader, type);
            reader.close();

            if (savedMap == null || savedMap.isEmpty()) return;

            int maxId = 0;
            for (Map.Entry<Integer, Game> entry : savedMap.entrySet()) {
                Game game = entry.getValue();
                int id = entry.getKey();

                LobbyController lc = new LobbyController(id, game.getPlayerConfig().getNum());
                lc.setModel(game);
                lc.setState(new LobbyPausedState(lc));

                savedLobbies.put(id, lc);
                if (id > maxId) maxId = id;
            }

            nextLobbyID.set(maxId + 1);

        } catch (IOException e) {
            System.err.println("[Boot] Errore caricamento: " + e.getMessage());
        }
    }

    //=============================================================================
    // Network related methods
    //=============================================================================

    public boolean startServer(String ip, int tcpPort, int rmiPort) throws FileNotFoundException {
        System.out.print("\033[H\033[2J");

        loadPersistedLobbies();
        persistLobbies();

        try {
            this.networkServer = new NetworkServer(ip, tcpPort);
            requestService.submit(networkServer);
            Logger.getInstance().print(LoggerLevel.SERVER, "TCP Server successfully started on " + ip + ":" + tcpPort);
        } catch (IOException | IllegalArgumentException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "TCP Server failed to start on " + ip + ":" + tcpPort);
            Logger.getInstance().print(LoggerLevel.ERROR, "Reason: " + e.getMessage());
            return false;
        }

        try {
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            Remote stub = UnicastRemoteObject.exportObject(this, 0);
            registry.rebind("mesos_server", stub);

            Logger.getInstance().print(LoggerLevel.SERVER, "RMI Server successfully started on " + ip + ":" + rmiPort);
        } catch (RemoteException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "RMI Server failed to start on " + ip + ":" + rmiPort);
            Logger.getInstance().print(LoggerLevel.ERROR, "Reason: " + e.getMessage());
            return false;
        }

        connectionMonitor.startClientMonitor();
        Logger.getInstance().print(LoggerLevel.SERVER, "Server successfully started");

        return true;
    }

    public void stopServer() {
        connectionMonitor.stop();

        for (ClientInterface client : allClients.values()) client.cleanup();
        networkServer.cleanup();
        RMICleanup();

        shutdownExecutor(requestService);
        shutdownExecutor(responseService);
        shutdownExecutor(persistenceService);

        Logger.getInstance().print(LoggerLevel.SERVER, "Server stopped");
    }

    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();

        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    public void disconnectClient(ClientInterface client) {
        writeLock.lock();
        try {
            ClientInterface removedClient = allClients.remove(client.getID());
            if (removedClient == null) return;

            playingClients.remove(client.getID());
            connectionMonitor.unregisterClient(client);

            removedClient.setConnected(false);
            removedClient.cleanup();

            LobbyController lobbyController = client.getCurrLobbyController();
            if (lobbyController != null) {
                lobbyController.getListeners().remove(client);
                lobbyController.remove(client);
            }
        } finally {
            writeLock.unlock();
        }

        Logger.getInstance().print(LoggerLevel.SERVER, "Client disconnected with ID: " + client.getID());
    }

    private void RMICleanup() {
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.unbind("mesos_server");
            UnicastRemoteObject.unexportObject(this, true);
            Logger.getInstance().print(LoggerLevel.SERVER, "RMI Server successfully closed");
        } catch (RemoteException | NotBoundException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to cleanly stop RMI server");
            Logger.getInstance().print(LoggerLevel.ERROR, "Reason: " + e.getMessage());
        }
    }


    public void submitRequest(Runnable task) {
        requestService.submit(task);
    }

    public void submitResponse(Runnable task) {
        responseService.submit(task);
    }

    @Override
    public void ping(String clientID) {
        readLock.lock();
        try {
            ClientInterface client = allClients.get(clientID);
            if (client == null) return;

            connectionMonitor.updateClientLastSeen(client);
            client.ping();
        } finally {
            readLock.unlock();
        }
    }
}