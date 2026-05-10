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
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.tui.Formatter;

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
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
    private final ExecutorService taskExecutor = Executors.newSingleThreadExecutor();

    private Lobby currLobby = null;
    private final Map<Integer, Lobby> waitingLobbies = new ConcurrentHashMap<>();

    //=============================================================================
    // Lobby management methods
    //=============================================================================

    @Override
    public synchronized void showWaitingLobbies(int clientID, List<Lobby> lobbies) {

        waitingLobbies.clear();

        for (Lobby lobby : lobbies)
            waitingLobbies.put(lobby.getLobbyID(), lobby);

        view.update();
    }

    @Override
    public synchronized void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {

        Lobby lobby = waitingLobbies.get(lobbyID);

        if (lobby != null) {
            Map<Player, Integer> playerInfo = new HashMap<>();
            currLobby = lobby;

            for (Map.Entry<Integer, Player> entry : players.entrySet()) {
                Player newKey = entry.getValue();
                Integer newValue = entry.getKey() < 0 ? null : entry.getKey();
                playerInfo.put(newKey, newValue);
            }

            currLobby.setPlayers(playerInfo);
        } else
            showError(clientID, "This lobby is not available");

        view.update();
    }

    @Override
    public synchronized void addClient(int clientID, int lobbyID, Player player) {

        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.addClient(clientID, player);

        view.update();
    }

    @Override
    public synchronized void addPlayer(int clientID, int lobbyID, Player player) {

        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.addPlayer(clientID, player);

        view.update();
    }

    @Override
    public synchronized void removeClient(int clientID, int lobbyID, Player player) {

        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.removeClient(clientID, player);
        view.update();
    }

    @Override
    public synchronized void removePlayer(int clientID, int lobbyID, Player player) {

        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.removePlayer(clientID, player);

        view.update();
    }

    @Override
    public synchronized void createLobby(int clientID, Lobby lobby, Player player) {

        currLobby = lobby;

        Map<Player, Integer> players = new HashMap<>();
        players.put(player, clientID);

        currLobby.setPlayers(players);

        view.update();
    }

    @Override
    public synchronized void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {

        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            waitingLobbies.clear();

            currLobby.initGame(tribes, board);
            view.transitionTo(ScreenType.GAME_PLAY);
        }
    }

    @Override
    public synchronized void stopLobby(int clientID, int lobbyID) {

        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            view.transitionTo(ScreenType.LOBBY_SELECTION);
        }
    }

    @Override
    public void showError(int clientID, String error) {
        view.displayError(error);
    }

    //=============================================================================
    // Game related methods
    //=============================================================================

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {

    }

    @Override
    public synchronized void updateState(int clientID, int lobbyID, ModelStateInfo modelStateInfo) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            Player player = currLobby.getPlayer(id);
            TurnState turnState = modelStateInfo.getTurnState(player);
            currLobby.setTurnState(turnState);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int clientID, int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateOrderTile(orderTile);
            currLobby.updateOfferTrack(offerTrack);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Board board) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateTribe(player, tribe);
            currLobby.updateBoard(board);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Row topRow) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateTribe(player, tribe);
            currLobby.updateTopRow(topRow);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int clientID, int lobbyID, Map<Integer, Tribe> tribes, Row topRow, Row bottomRow) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateTribes(tribes);
            currLobby.updateTopRow(topRow);
            currLobby.updateBottomRow(bottomRow);

            view.update();
        }
    }

    @Override
    public synchronized void updateModel(int clientID, int lobbyID, Map<Integer, Tribe> tribes, Map<Integer, Integer> ranking) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
            currLobby.updateTribes(tribes);
            currLobby.setRanking(ranking);

            view.update();
        }
    }

    //=============================================================================
    // Network related methods
    //=============================================================================

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
    public void setID(int clientID) {
        id = clientID;
        init = true;

        Logger.getInstance().print(LoggerLevel.CLIENT, "Received client ID: " + id);
    }

    public void setView(View view) {
        this.view = view;
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public int getID() {
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
