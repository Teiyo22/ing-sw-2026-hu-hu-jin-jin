package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.*;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.rmi.NotBoundException;
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

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

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

        clients.put(id, wrapper);
        connectionMonitor.registerClient(wrapper);

        wrapper.setConnected(true);
        wrapper.setID(id);
    }

    public void addClient(TCPClientInterface client) {
        int id = nextClientID.getAndIncrement();

        clients.put(id, client);
        connectionMonitor.registerClient(client);

        client.setConnected(true);
        client.setID(id);
    }

    public void disconnectClient(ClientInterface client) {
        client.setConnected(false);

        clients.remove(client.getID());
        connectionMonitor.unregisterClient(client);

        removeFromLobbies(client, client.getCurrLobbyID());
    }

    private void removeFromLobbies(ClientInterface removedClient, int lobbyID) {
        LobbyController lobbyController;

        writeLock.lock();
        lobbyController = waitingLobbies.get(lobbyID);

        if (lobbyController != null && lobbyController.removeFromLobby(removedClient))
            if (lobbyController.isEmpty())
                removeWaitingLobby(lobbyID);

        lobbyController = runningLobbies.get(lobbyID);
        if (lobbyController != null && lobbyController.removeFromLobby(removedClient))
            runningLobbies.remove(lobbyID);

        writeLock.unlock();
    }

    @Override
    public void createLobby(int clientID, int playerNum, Player player) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        int id = nextLobbyID.getAndIncrement();

        LobbyController lobbyController = new LobbyController(id, playerNum);
        lobbyController.addPlayer(client, player);

        writeLock.lock();
        waitingLobbies.put(lobbyController.getID(), lobbyController);
        client.createLobby(clientID, lobbyController.getLobby(), player);
        writeLock.unlock();
    }

    @Override
    public void joinLobby(int clientID, int lobbyID, Player player) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        LobbyController lobbyController = waitingLobbies.get(lobbyID);

        if (lobbyController != null)
            lobbyController.joinLobby(client, player);
        else
            ; // TODO : send error message to client
        readLock.unlock();
    }

    @Override
    public void leaveLobby(int clientID, int lobbyID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        writeLock.lock();
        removeFromLobbies(client, lobbyID);
        writeLock.unlock();
    }

    @Override
    public void startLobby(int clientID, int lobbyID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        writeLock.lock();
        LobbyController lobbyController = waitingLobbies.get(lobbyID);

        if (lobbyController == null)
            ; // TODO : send error message to client
        else if (lobbyController.startLobby(client)) {
            removeWaitingLobby(lobbyID);
            runningLobbies.put(lobbyID, lobbyController);
        }
        writeLock.unlock();
    }

    private void removeWaitingLobby(int lobbyID) {
        waitingLobbies.remove(lobbyID);

        for (ClientInterface client : clients.values())
            client.removeLobby(lobbyID);
    }

    @Override
    public void getWaitingLobbies(int clientID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        List<Lobby> lobbies = new ArrayList<>();

        readLock.lock();
        for (LobbyController lobbyController : waitingLobbies.values())
            lobbies.add(lobbyController.getLobby());

        client.showWaitingLobbies(clientID, lobbies);
        readLock.unlock();
    }

    @Override
    public void getLobbyInfo(int clientID, int lobbyID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        LobbyController lobbyController = waitingLobbies.get(lobbyID);

        if (lobbyController != null)
            lobbyController.getLobbyInfo(client);
        else
            ; // TODO : send error message to client
        readLock.unlock();
    }


    @Override
    public void getRank(int clientID, int lobbyID) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        LobbyController lobby = runningLobbies.get(lobbyID);
        if (lobby != null)
            lobby.showRank(client);
        else
            client.removeLobby(lobbyID);
    }

    @Override
    public void getLeaderboard(int clientID, int playerNum) {

    }

    @Override
    public void requestCards(int clientID, int lobbyID, List<Pickable> topPicks, List<Pickable> bottomPicks) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;


        readLock.lock();
        LobbyController lobby = runningLobbies.get(lobbyID);

        if (lobby != null)
            lobby.pickCards(client, topPicks, bottomPicks);
        else
            client.removeLobby(lobbyID);
        readLock.unlock();
    }

    @Override
    public void requestOffer(int clientID, int lobbyID, int offerIndex) {
        ClientInterface client = clients.get(clientID);

        if (client == null)
            return;

        readLock.lock();
        LobbyController lobby = runningLobbies.get(lobbyID);

        if (lobby != null)
            lobby.pickOffer(client, offerIndex);
        else
            client.removeLobby(lobbyID);
        readLock.unlock();
    }

    public void startServer(String ip, int tcpPort, int rmiPort) {
        try {
            this.networkServer = new NetworkServer(ip, tcpPort);
            listenerService.submit(networkServer);
            Logger.getInstance().print(LoggerLevel.SERVR, "TCP Server successfully started on " + ip + tcpPort);
        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "TCP Server failed to start on " + ip + tcpPort);
            System.exit(-1);
        }

        try {
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            registry.rebind("mesos_server", this);
            UnicastRemoteObject.exportObject(this, rmiPort);
            Logger.getInstance().print(LoggerLevel.SERVR, "RMI Server successfully started on " + ip + tcpPort);
        } catch (RemoteException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "RMI Server failed to start on " + ip + tcpPort);
            System.exit(-1);
        }

        connectionMonitor.start();
        Logger.getInstance().print(LoggerLevel.SERVR, "Server successfully started");
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
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to cleanly stop RMI server");
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