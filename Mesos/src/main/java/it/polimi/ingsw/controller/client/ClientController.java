package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.network.NetworkClient;
import it.polimi.ingsw.controller.client.network.RMIServerInterface;
import it.polimi.ingsw.controller.client.network.ServerInterface;
import it.polimi.ingsw.controller.client.network.TCPServerInterface;
import it.polimi.ingsw.controller.client.state.ClientState;
import it.polimi.ingsw.controller.client.state.NetworkSelectionState;
import it.polimi.ingsw.controller.client.state.gameplay.GamePlayState;
import it.polimi.ingsw.controller.client.state.lobby.LobbyInfoState;
import it.polimi.ingsw.controller.client.state.lobby.LobbyListState;
import it.polimi.ingsw.controller.client.state.lobby.LobbyModeState;
import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.*;

public class ClientController implements VirtualClient {
    private int id = 0;
    private ServerInterface server = null;
    private ClientState clientState;
    private ScheduledExecutorService retryService = null;

    protected Lobby currLobby = null;
    private Map<Integer, Lobby> waitingLobbies = new ConcurrentHashMap<>();

    private final Object lock = new Object();

    public ClientController() {
        // TODO: missing view
        this.clientState = new NetworkSelectionState(this);
        this.clientState.updateView();
    }

    @Override
    public void removeLobby(int lobbyID) {
        waitingLobbies.remove(lobbyID);

        synchronized (lock) {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                currLobby = null;
                clientState = (waitingLobbies.isEmpty()) ? new LobbyModeState(this)
                        : new LobbyListState(this);
            }

            clientState.updateView();

        }
    }

    @Override
    public void showWaitingLobbies(int clientID, List<Lobby> lobbies) {
        waitingLobbies.clear();

        for (Lobby lobby : lobbies)
            waitingLobbies.put(lobby.getLobbyID(), lobby);

        synchronized (lock) {
            clientState = new LobbyListState(this);
            clientState.updateView();
        }
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        synchronized (lock) {
            Lobby lobby = waitingLobbies.get(lobbyID);
            if (lobby != null) {
                currLobby = lobby;
                lobby.setPlayers(players);
                clientState = new LobbyInfoState(this);
            } else
                clientState = new LobbyListState(this);

            clientState.updateView();
        }
    }

    @Override
    public void addToLobby(int clientID, int lobbyID, Player player) {
        synchronized (lock) {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.addPlayer(clientID, player);

            clientState.updateView();
        }
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        synchronized (lock) {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.removePlayer(clientID);

            clientState.updateView();
        }
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player) {
        synchronized (lock) {
            currLobby = lobby;
            currLobby.addPlayer(clientID, player);

            clientState = new LobbyInfoState(this);
            clientState.updateView();
        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        synchronized (lock) {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID && currLobby.contains(id)) {
                waitingLobbies.clear();

                currLobby.initGame(tribes, board);
                clientState = new GamePlayState(this, null);
                clientState.updateView();
            }
        }
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {

    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {

    }

    @Override
    public void updateModel(int clientID, Board board, Tribe updatedTribe) {
        synchronized (lock) {
            if (currLobby != null) {
                currLobby.updateBoard(board);
                currLobby.updateTribe(clientID, updatedTribe);
            }

            clientState.updateView();
        }
    }

    @Override
    public void ping() {
    }

    @Override
    public void setID(int clientID) {
        id = clientID;
        Logger.getInstance().print(LoggerLevel.CLIENT, "Received client ID: " + id);

        clientState = new LobbyModeState(this);
        clientState.updateView();
    }

    /**
     * Connecting to the server using RMI.
     */
    public void connectRMI(String ip, int rmiPort) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, rmiPort);
            VirtualServer serverStub = (VirtualServer) registry.lookup("mesos_server");
            this.server = new RMIServerInterface(this, serverStub);

            retryService = Executors.newScheduledThreadPool(1);

            VirtualClient stub = (VirtualClient) UnicastRemoteObject.exportObject(this, 0);
            server.addClient(stub);

            Logger.getInstance().print(LoggerLevel.CLIENT, "Successfully connected with RMI to server: " + ip + ":" + rmiPort);
        } catch (RemoteException | NotBoundException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to connect with RMI to server: " + ip + ":" + rmiPort);
            Logger.getInstance().print(LoggerLevel.ERROR, "Reason: " + e.getMessage());
        } catch (Exception e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to connect with RMI to server: " + ip + ":" + rmiPort);
            Logger.getInstance().print(LoggerLevel.ERROR, "Reason: " + e.getMessage());
        }
    }

    /**
     * Connecting to the server using TCP.
     * Creates the NetworkClient and the ServerTCPInterface, which initializes the server reference in the first.
     *
     */
    public void connectTCP(String ip, int tcpPort) {
        NetworkClient networkClient = new NetworkClient();
        this.server = new TCPServerInterface(this, networkClient);
        try {
            networkClient.connect(ip, tcpPort);
            Logger.getInstance().print(LoggerLevel.CLIENT, "Successfully connected with TCP to server: " + ip + ":" + tcpPort);
        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to connect with RMI to server: " + ip + ":" + tcpPort);
            Logger.getInstance().print(LoggerLevel.ERROR, "Reason: " + e.getMessage());
        }
    }

    public synchronized void scheduleRetry(Runnable runnable){
        if(retryService != null && !retryService.isShutdown())
            retryService.schedule(runnable, 3, TimeUnit.SECONDS);
    }


    public synchronized void disconnect() {
        server.disconnect();
        currLobby = null;
        waitingLobbies.clear();

        if(retryService != null && !retryService.isShutdown())
            retryService.shutdown();
        retryService = null;

        Logger.getInstance().print(LoggerLevel.CLIENT, "Disconnected from server");

        clientState = new NetworkSelectionState(this);
        clientState.updateView();
    }
}
