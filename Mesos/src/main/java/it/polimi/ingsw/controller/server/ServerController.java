package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.ConnectionMonitor;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerController implements VirtualServer {
    private static ServerController instance;

    private NetworkServer networkServer;
    private final ConnectionMonitor connectionMonitor = new ConnectionMonitor();
    private final ExecutorService listenerService = Executors.newFixedThreadPool(28);
    private final ExecutorService responseService = Executors.newFixedThreadPool(28);
    private final ScheduledExecutorService retryService = Executors.newScheduledThreadPool(4);

    private final long retryDelay = 3L;

    private final Map<Integer, LobbyController> lobbies = new ConcurrentHashMap<>();
    private final Map<Integer, LobbyController> savedLobbies = new ConcurrentHashMap<>();

    private final Map<String, ClientInterface> allClients = new ConcurrentHashMap<>();
    private final Map<String, ClientInterface> playingClients = new ConcurrentHashMap<>();

    private final AtomicInteger nextLobbyID = new AtomicInteger(1);

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
        Logger.getInstance().print(LoggerLevel.SERVER, "Received request to create lobby with " + playerNum + " players from client " + clientID);
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        LobbyController currLobbyController = client.getCurrLobbyController();
        if (currLobbyController != null && currLobbyController.getLobby().containsClient(clientID)) {
            client.showError("You are already in a lobby");
            return;
        }

        int id = nextLobbyID.getAndIncrement();

        Player player = new Player(clientID, totem);
        LobbyController lobbyController = new LobbyController(id, playerNum);
        lobbyController.getPlayers().put(client, player);
        lobbyController.getListeners().add(client);

        writeLock.lock();
        lobbies.put(lobbyController.getID(), lobbyController);
        client.createLobby(lobbyController.getLobby(), player);
        writeLock.unlock();

        broadcastLobbyAddition(lobbyController.getLobby());
        Logger.getInstance().print(LoggerLevel.SERVER, "Created lobby " + lobbyController.getID() + " for client " + clientID + " with " + playerNum + " players");
    }

    @Override
    public void joinLobby(String clientID, int lobbyID, Totem totem) {
        Logger.getInstance().print(LoggerLevel.SERVER, "Received request to join lobby " + lobbyID + " from client " + clientID);
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        LobbyController lobbyController = lobbies.get(lobbyID);

        if (lobbyController != null) {
            Player player = new Player(clientID, totem);
            lobbyController.joinLobby(client, player);
        }
        else
            client.showError("This lobby is not available");
        readLock.unlock();
    }

    @Override
    public void leaveLobby(String clientID, int lobbyID) {
        Logger.getInstance().print(LoggerLevel.SERVER, "Received request to leave lobby " + lobbyID + " from client " + clientID);
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        if (!removeClientFrom(client, lobbyID))
            removeClientFromAll(client);
    }

    @Override
    public void startLobby(String clientID, int lobbyID) {
        Logger.getInstance().print(LoggerLevel.SERVER, "Received request to start lobby " + lobbyID + " from client " + clientID);
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        LobbyController lobbyController = lobbies.get(lobbyID);

        if (lobbyController != null)
            lobbyController.startLobby(client);
        else
            client.showError("This lobby is not available");
        readLock.unlock();
    }

    @Override
    public void getWaitingLobbies(String clientID) {
        Logger.getInstance().print(LoggerLevel.SERVER, "Received request to get waiting lobbies from client " + clientID);
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        List<Lobby> lobbies = this.lobbies.values().stream()
                .filter(LobbyController::isShowable)
                .map(LobbyController::getLobby)
                .toList();

        client.showWaitingLobbies(lobbies);
        readLock.unlock();
    }

    @Override
    public void getLobbyInfo(String clientID, int lobbyID) {
        Logger.getInstance().print(LoggerLevel.SERVER, "Received request to get lobby info from client " + clientID);
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        LobbyController lobbyController;

        lobbyController = lobbies.get(lobbyID);

        if (lobbyController != null)
            lobbyController.getLobbyInfo(client);
        else
            client.showError("This lobby is not available");
        readLock.unlock();
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

    public void removeLobby(int lobbyID) {
        lobbies.remove(lobbyID);
    }

    //=============================================================================
    // Game interaction methods
    //=============================================================================

    @Override
    public void requestCards(String clientID, int lobbyID, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        LobbyController lobby = lobbies.get(lobbyID);

        if (lobby != null)
            lobby.pickCards(client, topPicks, bottomPicks);
        else
            client.showError("This lobby is not available");
        readLock.unlock();
    }

    @Override
    public void requestOffer(String clientID, int lobbyID, int offerIndex) {
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        LobbyController lobby = lobbies.get(lobbyID);

        if (lobby != null)
            lobby.pickOffer(client, offerIndex);
        else
            client.showError("This lobby is not available");
        readLock.unlock();
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
        if (!allClients.containsKey(clientID)) // If true, it could mean the client already logged in
            return;

        if (username == null || username.isBlank()) {
            allClients.get(clientID).showError("Username cannot be empty");
            return;
        }

        ClientInterface previousValue = allClients.putIfAbsent(username, allClients.get(clientID));

        if (previousValue == null) {
            ClientInterface loggedInClient = allClients.remove(clientID);
            loggedInClient.confirmLogin(username);

            readLock.lock();
            List<Lobby> lobbies = this.lobbies.values().stream()
                    .filter(LobbyController::isShowable)
                    .map(LobbyController::getLobby)
                    .toList();

            loggedInClient.showWaitingLobbies(lobbies);
            readLock.unlock();

            Logger.getInstance().print(LoggerLevel.SERVER, "Client " + clientID + " successfully logged in as " + username);
        } else {
            allClients.get(clientID).showError("Username already in use");
            Logger.getInstance().print(LoggerLevel.SERVER, "Client " + clientID + " failed to login as " + username);
        }

    }

    public void addToPlayingClients(Collection<ClientInterface> toMoveClients) {
        for (ClientInterface toMoveClient : toMoveClients)
            playingClients.put(toMoveClient.getID(), toMoveClient);
    }

    public void removeFromPlayingClients(Collection<ClientInterface> toMoveClients) {
        for (ClientInterface toMoveClient : toMoveClients) {
            playingClients.remove(toMoveClient.getID());

            readLock.lock();
            List<Lobby> lobbies = this.lobbies.values().stream()
                    .filter(LobbyController::isShowable)
                    .map(LobbyController::getLobby)
                    .toList();

            toMoveClient.showWaitingLobbies(lobbies);
            readLock.unlock();
        }
    }

    private boolean removeClientFrom(ClientInterface client, int lobbyID) {
        boolean removed = false;

        writeLock.lock();
        LobbyController lobbyController = lobbies.get(lobbyID);

        if (lobbyController != null) {
            Logger.getInstance().print(LoggerLevel.SERVER, "Removing client " + client.getID() + " from lobby " + lobbyID);
            removed = lobbyController.removeClient(client);
        }

        writeLock.unlock();

        return removed;
    }

    private void removeClientFromAll(ClientInterface client) {
        for (LobbyController lobbyController : lobbies.values())
            removeClientFrom(client, lobbyController.getID());
    }

    private boolean removeListenerFrom(ClientInterface client, int lobbyID) {
        boolean removed = false;

        readLock.lock();
        LobbyController lobbyController = lobbies.get(lobbyID);

        if (lobbyController != null)
            removed = lobbyController.getListeners().remove(client);
        readLock.unlock();

        return removed;
    }

    private void removeListenerFromAll(ClientInterface client) {
        for (LobbyController lobbyController : lobbies.values())
            removeListenerFrom(client, lobbyController.getID());
    }

    //=============================================================================
    // Network related methods
    //=============================================================================

    public boolean startServer(String ip, int tcpPort, int rmiPort) {
        System.out.print("\033[H\033[2J");

        try {
            this.networkServer = new NetworkServer(ip, tcpPort);
            listenerService.submit(networkServer);
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

        for (ClientInterface client : allClients.values())
            client.cleanup();

        networkServer.cleanup();
        RMICleanup();

        listenerService.shutdown();

        try {
            if (!listenerService.awaitTermination(5, TimeUnit.SECONDS)) {
                listenerService.shutdownNow();
            }
        } catch (InterruptedException e) {
            listenerService.shutdownNow();
        }

        retryService.shutdownNow();

        try {
            if (!retryService.awaitTermination(10, TimeUnit.SECONDS)) {
                retryService.shutdownNow();
            }
        } catch (InterruptedException e) {
            retryService.shutdownNow();
        }

        Logger.getInstance().print(LoggerLevel.SERVER, "Server stopped");
    }

    public void disconnectClient(ClientInterface client) {
        if (!client.isConnected())
            return;

        client.setConnected(false);
        client.cleanup();

        allClients.remove(client.getID());
        playingClients.remove(client.getID());
        connectionMonitor.unregisterClient(client);

        LobbyController lobbyController = client.getCurrLobbyController();
        if (lobbyController != null) {
            if (!removeClientFrom(client, lobbyController.getID()))
                removeClientFromAll(client);

            if (!removeListenerFrom(client, lobbyController.getID()))
                removeListenerFromAll(client);
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

    public void scheduleRetry(Runnable task) {
        if (!retryService.isShutdown())
            retryService.schedule(task, retryDelay, TimeUnit.SECONDS);
    }

    public void submitListener(Runnable task) {
        listenerService.submit(task);
    }

    public void submitResponse(Runnable task) {
        responseService.submit(task);
    }

    @Override
    public void ping(String clientID) {
        ClientInterface client = allClients.get(clientID);

        if (client == null)
            return;

        connectionMonitor.updateClientLastSeen(client);
        client.ping();
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public Map<Integer, LobbyController> getLobbies() {
        return lobbies;
    }

}