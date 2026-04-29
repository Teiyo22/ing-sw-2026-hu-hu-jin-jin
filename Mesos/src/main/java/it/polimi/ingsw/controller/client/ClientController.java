package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.network.NetworkClient;
import it.polimi.ingsw.controller.client.network.ServerTCPInterface;
import it.polimi.ingsw.controller.client.view.ViewStates;
import it.polimi.ingsw.controller.client.view.VirtualView;
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

public class ClientController extends VirtualClient{
    private VirtualView view;
    private VirtualServer server;
    private Lobby currLobby;
    private Map<Integer, Lobby> waitingLobbies;
    private final Object lobbiesLock = new Object();

    public ClientController() {
        this.server = null;
    }

    public void setView(VirtualView view) {
        this.view = view;
    }

    @Override
    public void setWaitingLobbies(int clientID, List<Lobby> lobbies) {
        synchronized (lobbiesLock) {
            for (Lobby lobby : lobbies)
                waitingLobbies.put(lobby.getLobbyID(), lobby);
        }

        view.transitionTo(ViewStates.LOBBY_SELECTION);
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        synchronized (lobbiesLock) {
            Lobby lobby = waitingLobbies.get(lobbyID);

            if(lobby != null) {
                currLobby = lobby;
                lobby.setPlayers(players);
            }
            // TODO: Handle missing lobby
        }

        view.update();
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player) {
        synchronized (lobbiesLock) {
            if(currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.addPlayer(clientID, player);
            // TODO: handle missing/wrong lobby
            // TODO: what happens if the player tries to join multiple lobbies?
        }

        view.transitionTo(ViewStates.LOBBY_WAITING);
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        synchronized (lobbiesLock) {
            if(currLobby != null && currLobby.getLobbyID() == lobbyID)
                currLobby.removePlayer(clientID);
        }

        view.transitionTo(ViewStates.LOBBY_SELECTION);
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player) {
        synchronized (lobbiesLock) {
            currLobby = lobby;
            currLobby.addPlayer(clientID, player);
        }

        view.transitionTo(ViewStates.LOBBY_WAITING);
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        synchronized (lobbiesLock) {
            if(currLobby != null && currLobby.getLobbyID() == lobbyID) {
                currLobby.initGame(tribes, board);
            }
        }

        view.transitionTo(ViewStates.ROUND_START);
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {

    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {

    }

    @Override
    public void confirmPick(int clientID, Board board, Tribe updatedTribe) {
        synchronized (lobbiesLock) {
            if (currLobby != null) {
                currLobby.updateBoard(board);
                currLobby.updateTribe(clientID, updatedTribe);
            }
        }
        view.update();
    }

    /** Connecting to the server using RMI.
     * @param registryName the name of the server in the registry.
     * */
    public void connectRMI(String registryName, String ip, int rmiPort){
        try {
            Registry registry = LocateRegistry.getRegistry(ip, rmiPort);
            this.server = (VirtualServer) registry.lookup(registryName);
            UnicastRemoteObject.exportObject(this, rmiPort);
            server.addClient(this);
            server.getWaitingLobbies(super.getID());
        } catch (RemoteException e) {
            System.out.println("Error in connecting RMI server: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("Error in connecting RMI server: " + e.getMessage());
        }
    }

    /** Connecting to the server using TCP.
     * Creates the NetworkClient and the ServerTCPInterface, which initializes the server reference in the first.
     * */
    public void connectTCP(String ip, int tcpPort) {
        NetworkClient networkClient = new NetworkClient();
        this.server = new ServerTCPInterface(this, networkClient);
        try {
            networkClient.connect(ip, tcpPort);
            server.getWaitingLobbies(super.getID());
        } catch (UnknownHostException e) {
            System.out.println("Error in connecting TCP server: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error in connecting TCP server: " + e.getMessage());
        }
    }
}
