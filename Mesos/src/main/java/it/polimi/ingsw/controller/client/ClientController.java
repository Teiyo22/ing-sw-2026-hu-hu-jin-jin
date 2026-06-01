package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.network.NetworkClient;
import it.polimi.ingsw.controller.client.network.RMIServerInterface;
import it.polimi.ingsw.controller.client.network.ServerInterface;
import it.polimi.ingsw.controller.client.network.TCPServerInterface;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.model.gameState.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.common.*;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.server.network.RMIClientInterface;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.utils.LeaderboardResult;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.utils.view.Formatter;

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ClientController implements VirtualClient {
    private volatile String id = "";
    private volatile boolean init = false;

    private View view = null;
    private ServerInterface server = null;

    private final ConnectionMonitor connectionMonitor = new ConnectionMonitor();
    private final ExecutorService requestService = Executors.newVirtualThreadPerTaskExecutor();

    private Lobby currLobby = null;
    private final Map<Integer, Lobby> waitingLobbies = new ConcurrentHashMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    //=============================================================================
    // Lobby management methods
    //=============================================================================

    @Override
    public void confirmLogin(String username) {
        writeLock.lock();
        try {
            id = username;
            view.transitionTo(ScreenType.LOBBY_SELECTION);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void showWaitingLobbies(List<Lobby> lobbies) {
        writeLock.lock();
        try {
            waitingLobbies.clear();

            for (Lobby lobby : lobbies)
                waitingLobbies.put(lobby.getLobbyID(), lobby);

            if (currLobby != null)
                waitingLobbies.put(currLobby.getLobbyID(), currLobby);

            view.notifyChange();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {
        writeLock.lock();
        try {
            Lobby lobby = waitingLobbies.get(lobbyID);

            if (lobby != null) {
                Map<Player, Boolean> players = new HashMap<>();
                currLobby = lobby;

                for (Player player : connectedPlayers)
                    players.put(player, true);

                for (Player player : disconnectedPlayers)
                    players.put(player, false);

                currLobby.setPlayers(players);
                view.notifyChange();
            } else
                showError(new ErrorMessage("LobbyInfo", "This lobby is not available"));
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addPlayer(int lobbyID, Player player) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.addPlayer(player);

            view.notifyChange();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addLobby(Lobby lobby) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobby.getLobbyID()) {
                currLobby.setPlayerCount(lobby.getPlayerCount());
                lobby = currLobby;
            }

            waitingLobbies.put(lobby.getLobbyID(), lobby);
            view.notifyChange();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeLobby(int lobbyID) {
        writeLock.lock();
        try {
            waitingLobbies.remove(lobbyID);

            if (currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby = null;

            view.notifyChange();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void updateLobby(Lobby lobby) {
        writeLock.lock();
        try {
            if (waitingLobbies.containsKey(lobby.getLobbyID()))
                waitingLobbies.get(lobby.getLobbyID()).setPlayerCount(lobby.getPlayerCount());

            view.notifyChange();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeClient(int lobbyID, Player player) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.removeClient(player);
            view.notifyChange();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removePlayer(int lobbyID, Player player) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.removePlayer(player);

            view.notifyChange();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void createLobby(Lobby lobby, Player player) {
        writeLock.lock();
        try {
            currLobby = lobby;
            waitingLobbies.put(lobby.getLobbyID(), lobby);

            Map<Player, Boolean> players = new HashMap<>();
            players.put(player, true);

            currLobby.setPlayers(players);
            view.notifyChange();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void startLobby(int lobbyID, Board board, List<Player> players) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                waitingLobbies.clear();

                currLobby.initGame(players, board);
                view.transitionTo(ScreenType.GAME_PLAY);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void stopLobby(int lobbyID) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                currLobby.stop();
                view.transitionTo(ScreenType.LOBBY_SELECTION);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void showError(ErrorMessage errorMsg) {
        view.displayError(errorMsg);
    }

    @Override
    public void showEventResults(EventResultMessage eventResultMessage) {
        writeLock.lock();
        try {
            if (currLobby != null && eventResultMessage.getLobbyID() == currLobby.getLobbyID())
                view.displayEventResult(eventResultMessage);
        } finally {
            writeLock.unlock();
        }
    }

    //=============================================================================
    // Game related methods
    //=============================================================================

    @Override
    public void showLeaderboard(LeaderboardResult leaderboardResult) {
        writeLock.lock();
        try {
            if (currLobby != null) {
                currLobby.setLeaderboard(leaderboardResult);
                view.notifyChange();
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
        writeLock.lock();

        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                Player player = currLobby.getPlayer(id);
                TurnState turnState = modelStateInfo.getTurnState(player);
                currLobby.setTurnState(turnState);

                view.notifyChange();
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void updateModel(int lobbyID, Player player, int offerIndex) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                currLobby.resolveOfferPick(player, offerIndex);

                view.notifyChange();
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void updateModel(int lobbyID, Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                currLobby.resolveCardPicks(player, topRowPicks, bottomRowPicks);
                currLobby.updateTribe(player);

                view.notifyChange();
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players, Row topRow) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                currLobby.updateTribes(players);
                currLobby.updateRows(topRow);

                view.notifyChange();
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players) {
        writeLock.lock();
        try {
            if (currLobby != null && currLobby.getLobbyID() == lobbyID) {
                currLobby.updateTribes(players);
                currLobby.setRanking(players);

                view.transitionTo(ScreenType.GAME_END);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void showPlayer(Player player) {
        writeLock.lock();
        try {
            if (currLobby != null)
                currLobby.showPlayer(player);
        } finally {
            writeLock.unlock();
        }
    }

    public void hidePlayer() {
        writeLock.lock();
        try {
            if (currLobby != null)
                currLobby.hidePlayer();
        } finally {
            writeLock.unlock();
        }
    }

    //=============================================================================
    // Server related methods
    //=============================================================================

    public void createLobby(int size, Totem totem) {
        String clientID;

        readLock.lock();
        try {
            clientID = id;
        } finally {
            readLock.unlock();
        }

        server.createLobby(clientID, size, totem);
    }

    public void joinLobby(Totem totem) {
        String clientID;
        int lobbyID;

        readLock.lock();
        try {
            if (currLobby == null) return;

            clientID = id;
            lobbyID = currLobby.getLobbyID();
        } finally {
            readLock.unlock();
        }

        server.joinLobby(clientID, lobbyID, totem);
    }

    public void leaveLobby() {
        String clientID;
        int lobbyID;

        readLock.lock();
        try {
            if (currLobby == null) return;

            clientID = id;
            lobbyID = currLobby.getLobbyID();
        } finally {
            readLock.unlock();
        }

        server.leaveLobby(clientID, lobbyID);
    }

    public void getLobbyInfo(int lobbyID) {
        String clientID;

        readLock.lock();
        try {
            clientID = id;
        } finally {
            readLock.unlock();
        }

        server.getLobbyInfo(clientID, lobbyID);
    }

    public void login(String username) {
        String clientID;

        readLock.lock();
        try {
            clientID = id;
        } finally {
            readLock.unlock();
        }

        server.login(clientID, username);
    }

    public void requestAction(PlayerAction action) {
        String clientID;
        int lobbyID;

        readLock.lock();
        try {
            if (currLobby == null) return;

            clientID = id;
            lobbyID = currLobby.getLobbyID();
        } finally {
            readLock.unlock();
        }
        server.requestAction(clientID, lobbyID, action);
    }

    public void startLobby() {
        String clientID;
        int lobbyID;

        readLock.lock();
        try {
            if (currLobby == null) return;

            clientID = id;
            lobbyID = currLobby.getLobbyID();
        } finally {
            readLock.unlock();
        }

        server.startLobby(clientID, lobbyID);
    }

    public void getLeaderboard(){
        String clientID;
        int lobbySize;

        readLock.lock();
        try {
            if (currLobby == null) return;
            clientID = id;
            lobbySize = currLobby.getSize();
            view.transitionTo(ScreenType.LEADERBOARD);

            if (currLobby.getLeaderboard() == null)
                server.getLeaderboard(clientID, lobbySize);

        } finally {
            readLock.unlock();
        }
    }

    public String getID() {
        readLock.lock();
        try {
            return id;
        } finally {
            readLock.unlock();
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
            server = new RMIServerInterface(this, serverStub);
            server.setConnected(true);

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
            server.setConnected(true);

            Logger.getInstance().print(LoggerLevel.CLIENT, "Successfully connected with TCP to server: " + ip + ":" + tcpPort);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Failed to connect with TCP to server: " + ip + ":" + tcpPort);
            System.out.println("Reason: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void disconnect() {
        init = false;

        view.close();

        server.disconnect();
        connectionMonitor.stop();
        requestService.shutdown();

        try {
            if (!requestService.awaitTermination(2, TimeUnit.SECONDS)) {
                requestService.shutdownNow();
            }
        } catch (InterruptedException e) {
            requestService.shutdownNow();
        }

        Formatter.clearScreen();
        System.exit(0);
    }

    public void submitRequest(Runnable task) {
        requestService.submit(task);
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

    public void setIdleTurnState() {
        writeLock.lock();
        try {
            if (currLobby != null)
                currLobby.setIdleTurnState();
        } finally {
            writeLock.unlock();
        }
    }



    //=============================================================================
    // Getters
    //=============================================================================

    /**
     * @return a copy of the current waiting lobbies.
     *
     */
    public HashMap<Integer, Lobby> getWaitingLobbies() {
        readLock.lock();
        try {
            return new HashMap<>(waitingLobbies);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * @return if present returns a copy of the current lobby
     * containing all the information of the original except board,
     * otherwise returns null.
     *
     */
    public Lobby getCurrLobby() {
        readLock.lock();
        try {
            if (currLobby != null)
                return currLobby.copy();

            return null;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * @return if present returns a copy of the current board
     * with references to the original components,
     * otherwise returns null.
     *
     */
    public Board getBoard() {
        readLock.lock();
        try {
            if (currLobby == null || currLobby.getBoard() == null)
                return null;
            return currLobby.getBoard().mediumCopy();
        } finally {
            readLock.unlock();
        }
    }


    public boolean isInit() {
        return init;
    }
}
