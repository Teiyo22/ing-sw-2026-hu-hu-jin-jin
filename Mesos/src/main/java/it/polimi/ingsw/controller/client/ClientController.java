package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.network.NetworkClient;
import it.polimi.ingsw.controller.client.network.RMIServerInterface;
import it.polimi.ingsw.controller.client.network.ServerInterface;
import it.polimi.ingsw.controller.client.network.TCPServerInterface;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.common.*;
import it.polimi.ingsw.controller.server.network.RMIClientInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.tui.Formatter;

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;
import java.util.concurrent.*;


public class ClientController implements VirtualClient {
    private String id = "";
    private boolean init = false;

    private View view = null;
    private ServerInterface server = null;

    private final ConnectionMonitor connectionMonitor = new ConnectionMonitor();
    private final ExecutorService taskExecutor = Executors.newSingleThreadExecutor();

    private Lobby currLobby = null;
    private final Map<Integer, Lobby> waitingLobbies = new ConcurrentHashMap<>();

    //=============================================================================
    // Lobby management methods
    //=============================================================================

    @Override
    public void confirmLogin(String username) {
        id = username;
        view.transitionTo(ScreenType.LOBBY_SELECTION);
    }

    @Override
    public synchronized void showWaitingLobbies(List<Lobby> lobbies) {
        waitingLobbies.clear();

        for (Lobby lobby : lobbies)
            waitingLobbies.put(lobby.getLobbyID(), lobby);

        if (currLobby != null)
            waitingLobbies.put(currLobby.getLobbyID(), currLobby);

        view.update();
    }

    @Override
    public synchronized void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {

        Lobby lobby = waitingLobbies.get(lobbyID);

        if (lobby != null) {
            Map<Player, Boolean> players = new HashMap<>();
            currLobby = lobby;

            for (Player player : connectedPlayers)
                players.put(player, true);

            for (Player player : disconnectedPlayers)
                players.put(player, false);

            currLobby.setPlayers(players);
        } else
            showError("This lobby is not available");

        view.update();
    }

    @Override
    public synchronized void addPlayer(int lobbyID, Player player) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.addPlayer(player);

        view.update();
    }

    @Override
    public synchronized void addLobby(Lobby lobby) {
        if (currLobby != null && currLobby.getLobbyID() == lobby.getLobbyID()) {
            currLobby.setPlayerCount(lobby.getPlayerCount());
            lobby = currLobby;
        }

        waitingLobbies.put(lobby.getLobbyID(), lobby);

        view.update();
    }

    @Override
    public synchronized void removeLobby(int lobbyID) {
        waitingLobbies.remove(lobbyID);

        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby = null;

        view.update();
    }

    @Override
        public synchronized void updateLobby(Lobby lobby) {
        if (waitingLobbies.containsKey(lobby.getLobbyID()))
            waitingLobbies.get(lobby.getLobbyID()).setPlayerCount(lobby.getPlayerCount());

        view.update();
    }

    @Override
    public synchronized void removeClient(int lobbyID, Player player) {

        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.removeClient(player);
        view.update();
    }

    @Override
    public synchronized void removePlayer(int lobbyID, Player player) {

        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.removePlayer(player);

        view.update();
    }

    @Override
    public synchronized void createLobby(Lobby lobby, Player player) {
        currLobby = lobby;
        waitingLobbies.put(lobby.getLobbyID(), lobby);

        Map<Player, Boolean> players = new HashMap<>();
        players.put(player, true);

        currLobby.setPlayers(players);

        view.update();
    }

    @Override
    public synchronized void startLobby(int lobbyID, Board board, List<Player> players) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            waitingLobbies.clear();

            currLobby.initGame(players, board);
            view.transitionTo(ScreenType.GAME_PLAY);
        }
    }

    @Override
    public synchronized void stopLobby(int lobbyID) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            view.transitionTo(ScreenType.LOBBY_SELECTION);
        }
    }

    @Override
    public void showError(String error) {
        view.displayError(error);
    }

    //=============================================================================
    // Game related methods
    //=============================================================================

    @Override
    public void showLeaderboard(List<LeaderboardEntry> leaderboard) {

    }

    @Override
    public synchronized void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            Player player = currLobby.getPlayer(id);
            TurnState turnState = modelStateInfo.getTurnState(player);
            currLobby.setTurnState(turnState);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateOrderTile(orderTile);
            currLobby.updateOfferTrack(offerTrack);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int lobbyID, Player player, Board board) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateTribe(player);
            currLobby.updateBoard(board);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int lobbyID, Player player, Row topRow) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateTribe(player);
            currLobby.updateTopRow(topRow);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int lobbyID, List<Player> players, Row topRow, Row bottomRow) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateTribes(players);
            currLobby.updateTopRow(topRow);
            currLobby.updateBottomRow(bottomRow);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int lobbyID, List<Player> players) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateTribes(players);
            currLobby.setRanking(players);

            view.update();
        }
    }

    //=============================================================================
    // Network related methods
    //=============================================================================

    /**
     * Connecting to the server using RMI.
     */
    public boolean connectRMI(String ip, int rmiPort) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, rmiPort);
            VirtualServer serverStub = (VirtualServer) registry.lookup("mesos_server");
            this.server = new RMIServerInterface(this, serverStub);

            VirtualClient stub = (VirtualClient) UnicastRemoteObject.exportObject(this, 0);
            server.registerClient(new RMIClientInterface(stub));

            Logger.getInstance().print(LoggerLevel.CLIENT, "Successfully connected with RMI to server: " + ip + ":" + rmiPort);
        } catch (RemoteException | NotBoundException e) {
            Logger.getInstance().print(LoggerLevel.ERROR, "Failed to connect with RMI to server: " + ip + ":" + rmiPort);
            Logger.getInstance().print(LoggerLevel.ERROR, "Reason: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Connecting to the server using TCP.
     * Creates the NetworkClient and the ServerTCPInterface, which initializes the server reference in the first.
     *
     */
    public boolean connectTCP(String ip, int tcpPort) {
        NetworkClient networkClient = new NetworkClient();
        this.server = new TCPServerInterface(this, networkClient);

        try {
            networkClient.connect(ip, tcpPort);
            Logger.getInstance().print(LoggerLevel.CLIENT, "Successfully connected with TCP to server: " + ip + ":" + tcpPort);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Failed to connect with TCP to server: " + ip + ":" + tcpPort);
            System.out.println("Reason: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void disconnect() {
        if (!init)
            return;

        init = false;

        view.close();

        server.disconnect();
        connectionMonitor.stop();
        taskExecutor.shutdown();

        try {
            if (!taskExecutor.awaitTermination(3, TimeUnit.SECONDS)) {
                taskExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            taskExecutor.shutdownNow();
        }

        Formatter.clearScreen();
        System.out.println("Client disconnected: press 'Enter' to exit...");

        Logger.getInstance().print(LoggerLevel.CLIENT, "Disconnected from server");
    }

    public void executeCommand(Runnable command) {
        taskExecutor.submit(command);
    }

    @Override
    public void ping() {
        connectionMonitor.updateServerLastSeen();
    }

    //=============================================================================
    // Setters
    //=============================================================================

    @Override
    public void setID(String clientID) {
        this.id = clientID;
        init = true;
        connectionMonitor.startServerMonitor(this);

        Logger.getInstance().print(LoggerLevel.CLIENT, "Received client ID: " + this.id);
    }

    public void setView(View view) {
        this.view = view;
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public String getID() {
        return id;
    }

    public ServerInterface getServer() {
        return server;
    }

    public HashMap<Integer, Lobby> getWaitingLobbies() {
        return new HashMap<>(waitingLobbies);
    }

    public Lobby getCurrLobby() {
        return currLobby;
    }

    public boolean isInit() {
        return init;
    }
}
