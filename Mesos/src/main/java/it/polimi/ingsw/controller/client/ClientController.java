package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.network.NetworkClient;
import it.polimi.ingsw.controller.client.network.RMIServerInterface;
import it.polimi.ingsw.controller.client.network.ServerInterface;
import it.polimi.ingsw.controller.client.network.TCPServerInterface;
import it.polimi.ingsw.controller.common.*;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.*;


public class ClientController implements VirtualClient {
    private int id = 0;
    private boolean init = false;

    private View view = null;
    private ServerInterface server = null;

    private final ConnectionMonitor connectionMonitor = new ConnectionMonitor();
    private final ExecutorService taskExecutor = Executors.newFixedThreadPool(5);


    private Lobby currLobby = null;
    private final Map<Integer, Lobby> waitingLobbies = new ConcurrentHashMap<>();

    private final Object lock = new Object();


    @Override
    public void removeLobby(int lobbyID) {
        synchronized (lock) {
            waitingLobbies.remove(lobbyID);

            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                currLobby = null;

                view.transitionTo(ScreenType.LOBBY_SELECTION);
            }
        }
    }

    @Override
    public void showWaitingLobbies(int clientID, List<Lobby> lobbies) {
        synchronized (lock) {
            waitingLobbies.clear();

            for (Lobby lobby : lobbies)
                waitingLobbies.put(lobby.getLobbyID(), lobby);

            view.update();
        }
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        synchronized (lock) {
            Lobby lobby = waitingLobbies.get(lobbyID);

            if (lobby != null) {
                currLobby = lobby;
                lobby.setPlayers(players);
            } else
                ; // TODO: show error

            view.update();
        }
    }

    @Override
    public void addToLobby(int clientID, int lobbyID, Player player) {
        synchronized (lock) {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.addPlayer(clientID, player);

            view.update();
        }
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        synchronized (lock) {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.removePlayer(clientID);

            view.update();
        }
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player) {
        synchronized (lock) {
            currLobby = lobby;
            currLobby.addPlayer(clientID, player);

            view.update();
        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        synchronized (lock) {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                if (currLobby.contains(id)) {
                    waitingLobbies.clear();

                    currLobby.initGame(tribes, board);
                    view.transitionTo(ScreenType.GAME_PLAY);
                } else {
                    currLobby = null;
                    view.update();
                }
            }
        }
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {
//        this.rankings = rankings;
        view.transitionTo(ScreenType.GAME_END);
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

            view.transitionTo(ScreenType.GAME_PLAY); // TODO: depends on the game state
        }
    }

    @Override
    public void showError(int clientID, String error) {
//        view.renderError(errorMessage);
    }

    @Override
    public void ping() {
        connectionMonitor.updateServerLastSeen();
    }

    @Override
    public void setID(int clientID) {
        id = clientID;
        init = true;

        Logger.getInstance().print(LoggerLevel.CLIENT, "Received client ID: " + id);
    }

    public int getID() {
        return id;
    }

    /**
     * Connecting to the server using RMI.
     */
    public void connectRMI(String ip, int rmiPort) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, rmiPort);
            VirtualServer serverStub = (VirtualServer) registry.lookup("mesos_server");
            this.server = new RMIServerInterface(this, serverStub);

            VirtualClient stub = (VirtualClient) UnicastRemoteObject.exportObject(this, 0);
            server.addClient(stub);

            connectionMonitor.startServerMonitor(this);

            Logger.getInstance().print(LoggerLevel.CLIENT, "Successfully connected with RMI to server: " + ip + ":" + rmiPort);
        } catch (RemoteException | NotBoundException e) {
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
            connectionMonitor.startServerMonitor(this);
            Logger.getInstance().print(LoggerLevel.CLIENT, "Successfully connected with TCP to server: " + ip + ":" + tcpPort);
        } catch (IOException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to connect with TCP to server: " + ip + ":" + tcpPort);
            Logger.getInstance().print(LoggerLevel.ERROR, "Reason: " + e.getMessage());
        }
    }

    public boolean isInit() {
        return init;
    }

    public synchronized void disconnect() {
        server.disconnect();
        currLobby = null;
        waitingLobbies.clear();

        view.close();

        Logger.getInstance().print(LoggerLevel.CLIENT, "Disconnected from server");
    }

    public void setView(View view) {
        this.view = view;
    }

    public ServerInterface getServer() {
        return server;
    }

    public Lobby getCurrLobby() {
        synchronized (lock) {
            return currLobby == null ? null : currLobby.copy();
        }
    }

    public List<Player> getPlayers() {
        synchronized (lock) {
            return new ArrayList<>(currLobby.getPlayers().values());
        }
    }

    public synchronized Board getBoard() {
        synchronized (lock) {
            return currLobby.getBoard();
        }
    }

    public HashMap<Integer, Lobby> getWaitingLobbies() {
        synchronized (lock) {
            return new HashMap<>(waitingLobbies);
        }
    }
}
