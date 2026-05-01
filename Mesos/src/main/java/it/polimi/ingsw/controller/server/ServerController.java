package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.*;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ServerController extends VirtualServer {
    private static ServerController instance;

    private NetworkServer networkServer;
    private final ConnectionMonitor connectionMonitor = new ConnectionMonitor();
    private final ExecutorService listenerService = Executors.newFixedThreadPool(28);
    private final ScheduledExecutorService retryService = Executors.newScheduledThreadPool(4);

    private final long retryDelay = 3L;

    private final Map<Integer, LobbyController> waitingLobbies = new ConcurrentHashMap<>();
    private final Map<Integer, LobbyController> runningLobbies = new ConcurrentHashMap<>();
    private final Map<Integer, ClientInterface> clients = new ConcurrentHashMap<>();

    private final AtomicInteger nextClientID = new AtomicInteger(1);
    private final AtomicInteger nextLobbyID = new AtomicInteger(1);

    private ReentrantLock lock = new ReentrantLock(true);

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    @Override
    public void addClient(VirtualClient client) {
        RMIClientInterface wrapper = new RMIClientInterface(client);
        int id = nextClientID.getAndIncrement();

        wrapper.setConnected(true);
        wrapper.setID(id);

        clients.put(id, wrapper);
        connectionMonitor.registerClient(wrapper);
    }

    public void addClient(TCPClientInterface client) {
        int id = nextClientID.getAndIncrement();

        client.setConnected(true);
        client.setID(id);

        clients.put(id, client);
        connectionMonitor.registerClient(client);
    }

    public void disconnectClient(ClientInterface client) {
        clients.remove(client.getID());
        connectionMonitor.unregisterClient(client);
        client.setConnected(false);

        LobbyController lobbyController;

        lobbyController = runningLobbies.get(client.getCurrLobbyID());
        if (lobbyController != null)
            lobbyController.removePlayer(client);

        lobbyController = waitingLobbies.get(client.getCurrLobbyID());
        if (lobbyController != null)
            lobbyController.removePlayer(client);
    }

    @Override
    public void createLobby(int clientID, int playerNum, Player player) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        int id = nextLobbyID.getAndIncrement();

        LobbyController lobbyController = new LobbyController(id, playerNum);
        lobbyController.addPlayer(client, player);

        client.createLobby(clientID, lobbyController.getLobby(), player);
        waitingLobbies.put(lobbyController.getID(), lobbyController);
    }

    public void removeRunningLobby(int lobbyID) {
        runningLobbies.remove(lobbyID);
    }

    public void removeWaitingLobby(int lobbyID) {
        waitingLobbies.remove(lobbyID);

        for(ClientInterface clientInterface: clients.values())
            clientInterface.deleteLobby(lobbyID);
    }

    @Override
    public void joinLobby(int clientID, int lobbyID, Player player) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        LobbyController lobbyController = waitingLobbies.get(lobbyID);

        if (lobbyController != null)
            lobbyController.joinLobby(client, player);
        else
            client.deleteLobby(lobbyID);
    }

    @Override
    public void leaveLobby(int clientID, int lobbyID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        LobbyController lobbyController = waitingLobbies.get(lobbyID);

        if(lobbyController != null)
            lobbyController.removePlayer(client);
        else
            client.deleteLobby(lobbyID);
    }

    @Override
    public void startLobby(int clientID, int lobbyID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        LobbyController lobbyController = waitingLobbies.get(lobbyID);

        if (lobbyController == null)
            client.deleteLobby(lobbyID);
        else if (lobbyController.startLobby()) {
            waitingLobbies.remove(lobbyID, lobbyController);
            runningLobbies.put(lobbyID, lobbyController);
        }

        // TODO: handle already started lobby
    }

    @Override
    public void getWaitingLobbies(int clientID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        List<Lobby> lobbies = new ArrayList<>();

        for (LobbyController lobbyController : waitingLobbies.values())
            lobbies.add(lobbyController.getLobby());

        client.showWaitingLobbies(clientID, lobbies);
    }

    @Override
    public void getLobbyInfo(int clientID, int lobbyID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

         LobbyController lobbyController = waitingLobbies.get(lobbyID);

         if(lobbyController != null)
             lobbyController.getLobbyInfo(client);
         else
             client.deleteLobby(lobbyID);
    }


    @Override
    public void getRank(int clientID, int lobbyID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        LobbyController lobby = runningLobbies.get(lobbyID);
        if(lobby != null)
            lobby.showRank(client);
        else
            client.deleteLobby(lobbyID);
    }

    @Override
    public void getLeaderboard(int clientID, int playerNum) {

    }

    @Override
    public void requestCards(int clientID, int lobbyID, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        ClientInterface client = clients.get(clientID);

        if(client == null)
            return;

        LobbyController lobby = runningLobbies.get(lobbyID);

        if(lobby != null)
            lobby.pickCards(client, topPicks, bottomPicks);
        else
            client.deleteLobby(lobbyID);
    }

    @Override
    public void requestOffer(int clientID, int lobbyID, int offerIndex) {
        ClientInterface client = clients.get(clientID);

        if(client == null)
            return;

        LobbyController lobby = runningLobbies.get(lobbyID);

        if(lobby != null)
            lobby.pickOffer(client, offerIndex);
        else
            client.deleteLobby(lobbyID);
    }

    public void startServer(String ip, int tcpPort, int rmiPort) {
        try {
            this.networkServer = new NetworkServer(ip, tcpPort);
            listenerService.submit(networkServer);
            System.out.println("TCP Server started on" + ip + tcpPort);
        } catch (IOException e) {
            System.err.println("Failed to Start TCP Server:" + e.getMessage());
            System.exit(-1);
        }

        try {
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            registry.rebind("mesos_server", this);
            UnicastRemoteObject.exportObject(this, rmiPort);
            System.out.println("RMI server started on" + ip + rmiPort);
        } catch (RemoteException e) {
            System.err.println("Failed to start RMI server:" + e.getMessage());
            System.exit(-1);
        }

        connectionMonitor.start();
        System.out.println("Server started");
    }

    public void stopServer() {
        connectionMonitor.stop();
        listenerService.shutdownNow();
        retryService.shutdownNow();
        TCPCleanup();
        RMICleanup();
    }

    private void RMICleanup() {
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.unbind("mesos_server");
            UnicastRemoteObject.unexportObject(this, true);
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Failed to cleanly stop RMI server");
        }
    }

    private void TCPCleanup() {
        networkServer.cleanup();
    }

    public void scheduleRetry(Runnable task) {
        retryService.schedule(task, retryDelay, TimeUnit.SECONDS);
    }

    public void submitListener(Runnable task) {
        listenerService.submit(task);
    }
}