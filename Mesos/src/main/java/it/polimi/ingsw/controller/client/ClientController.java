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

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;

public class ClientController extends VirtualClient {
    private ServerInterface server = null;
    private ClientState clientState;

    protected Lobby currLobby = null;
    private Map<Integer, Lobby> waitingLobbies = new ConcurrentHashMap<>();
    private final Object lobbiesLock = new Object();

    public ClientController() {
        // TODO: missing view
        this.clientState = new NetworkSelectionState(this);
        this.clientState.updateView();
    }

    @Override
    public void stopLobby(int lobbyID) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID && currLobby.isStarted()) {
            currLobby = null;
            waitingLobbies = null;
            clientState = new LobbyModeState(this);
        }

        clientState.updateView();
    }

    @Override
    public void deleteLobby(int lobbyID) {
        waitingLobbies.remove(lobbyID);

        if (currLobby != null && currLobby.getLobbyID() == lobbyID && !currLobby.isStarted()) {
            currLobby = null;
            clientState = new LobbyListState(this);
        }

        clientState.updateView();
    }

    @Override
    public void showWaitingLobbies(int clientID, List<Lobby> lobbies) {
        waitingLobbies.clear();

        for (Lobby lobby : lobbies)
            waitingLobbies.put(lobby.getLobbyID(), lobby);

        clientState = new LobbyListState(this);
        clientState.updateView();
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        Lobby lobby = waitingLobbies.get(lobbyID);

        if (lobby != null) {
            currLobby = lobby;
            lobby.setPlayers(players);
            clientState = new LobbyInfoState(this);
        } else
            clientState = new LobbyListState(this);

        clientState.updateView();
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.addPlayer(clientID, player);

        clientState.updateView();
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.removePlayer(clientID);

        clientState.updateView();
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player) {
        currLobby = lobby;
        currLobby.addPlayer(clientID, player);

        clientState = new LobbyInfoState(this);
        clientState.updateView();
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        if (currLobby != null && currLobby.getLobbyID() == lobbyID)
            currLobby.initGame(tribes, board);

        clientState = new GamePlayState(this, null);
        clientState.updateView();
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {

    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {

    }

    @Override
    public void updateModel(int clientID, Board board, Tribe updatedTribe) {
        if (currLobby != null) {
            currLobby.updateBoard(board);
            currLobby.updateTribe(clientID, updatedTribe);
        }

        clientState.updateView();
    }

    @Override
    public void ping() {
    }

    @Override
    public void setID(int clientID) {
        id = clientID;
    }

    /**
     * Connecting to the server using RMI.
     *
     * @param registryName the name of the server in the registry.
     *
     */
    public void connectRMI(String registryName, String ip, int rmiPort) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, rmiPort);
            VirtualServer serverStub = (VirtualServer) registry.lookup(registryName);
            this.server = new RMIServerInterface(this, serverStub);

            UnicastRemoteObject.exportObject(this, rmiPort);
            server.addClient(this);
        } catch (RemoteException e) {
            System.out.println("Error in connecting RMI server: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("Error in connecting RMI server: " + e.getMessage());
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
        } catch (UnknownHostException e) {
            System.out.println("Error in connecting TCP server: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error in connecting TCP server: " + e.getMessage());
        }
    }
}
