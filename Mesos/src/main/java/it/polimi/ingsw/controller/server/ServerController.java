package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.ConnectionMonitor;
import it.polimi.ingsw.controller.server.network.NetworkServer;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerController extends VirtualServer {
    private static ServerController instance;

    private NetworkServer networkServer;
    private final ConnectionMonitor connectionMonitor = new ConnectionMonitor();

    private final Map<Integer, LobbyController> waitingLobbies = new ConcurrentHashMap<>();
    private final Map<Integer, LobbyController> runningLobbies = new ConcurrentHashMap<>();
    private final Map<Integer, VirtualClient> clients = new ConcurrentHashMap<>();

    private final AtomicInteger nextClientID = new AtomicInteger(1);
    private final AtomicInteger nextLobbyID = new AtomicInteger(1);

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock rlock = lock.readLock();
    private final Lock wlock = lock.writeLock();

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    // Should be synchronized
    @Override
    public void addClient(VirtualClient client) {
        int id = nextClientID.getAndIncrement();

        client.setID(id);
        clients.put(id, client);
        connectionMonitor.registerClient(client);
    }

    public void onClientDisconnected(VirtualClient client) {
        if (client == null)
            return;

        clients.remove(client.getID());

        // TODO: handle lobby synchronization
        for(LobbyController lobby: runningLobbies.values()) {
            lobby.removePlayer(client);
        }

        for(LobbyController lobby: waitingLobbies.values()) {
            lobby.removePlayer(client);
        }
    }

    // Should be synchronized
    @Override
    public void createLobby(int clientID, int playerNum, Player player) {
        VirtualClient client = clients.get(clientID);
        int id = nextLobbyID.getAndIncrement();

        LobbyController lobbyController = new LobbyController(id, playerNum);
        lobbyController.addPlayer(client, player);
        waitingLobbies.put(lobbyController.getID(), lobbyController);

        client.createLobby(clientID, lobbyController.getLobby(), player);
    }

    public void removeRunningLobby(int lobbyID) {
        runningLobbies.remove(lobbyID);
    }

    public void removeWaitingLobby(int lobbyID) {
        waitingLobbies.remove(lobbyID);
    }

    @Override
    public void joinLobby(int clientID, int lobbyID, Player player) {
        VirtualClient client = clients.get(clientID);

        LobbyController lobbyController = waitingLobbies.get(lobbyID);

        if (lobbyController != null && lobbyController.getPlayers().size() < lobbyController.getSize()) {
            lobbyController.addPlayer(client, player);
            lobbyController.joinLobby(clientID, player);
        }

        // TODO: Handle not joinable lobby

    }

    @Override
    public void leaveLobby(int clientID, int lobbyID) {
        VirtualClient client = clients.get(clientID);

        synchronized (lobbiesLock) {
            LobbyController lobbyController = waitingLobbies.get(lobbyID);
            lobbyController.removePlayer(client);
        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID) {
        if (waitingLobbies.containsKey(lobbyID)) {
            LobbyController lobbyController = waitingLobbies.get(lobbyID);
            lobbyController.startLobby();

            waitingLobbies.remove(lobbyID, lobbyController);
            runningLobbies.put(lobbyID, lobbyController);
        }
    }

    @Override
    public void getWaitingLobbies(int clientID) {
        VirtualClient client = clients.get(clientID);
        List<Lobby> lobbies = new ArrayList<>();

        for (LobbyController lobbyController : waitingLobbies.values()) {
            Lobby lobby = new Lobby(lobbyController.getID(), lobbyController.getSize());
            lobbies.add(lobby);
        }
        client.setWaitingLobbies(clientID, lobbies);
    }

    @Override
    public void getLobbyInfo(int clientID, int lobbyID) {
        VirtualClient client = clients.get(clientID);
        Map<Integer, Player> players = new HashMap<>();

        if (waitingLobbies.containsKey(lobbyID)) {
            LobbyController lobbyController = waitingLobbies.get(lobbyID);

            for (VirtualClient virtualClient : lobbyController.getPlayers().keySet()) {
                players.put(virtualClient.getID(), lobbyController.getPlayers().get(virtualClient));
            }

            client.showLobbyInfo(clientID, lobbyID, players);
        }
    }

    @Override
    public void getRank(int clientID, int lobbyID) {
        LobbyController lobby = runningLobbies.get(lobbyID);
        lobby.showRank(clientID);
    }

    @Override
    public void getLeaderboard(int clientID, int playerNum) {


    }

    @Override
    public void requestPick(int clientID, int lobbyID, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        LobbyController lobby = runningLobbies.get(lobbyID);
        lobby.pickCards(clients.get(clientID), topPicks, bottomPicks);
    }

    public void startServer(String ip, int tcpPort, int rmiPort) {
        try {
            this.networkServer = new NetworkServer(ip, tcpPort);
            this.networkServer.start();
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
        networkServer.interrupt();
    }
}