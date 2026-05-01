package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class RMIClientInterface extends ClientInterface {
    VirtualClient wrappedClient;

    public RMIClientInterface(VirtualClient wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    @Override
    public void setID(int clientID)  {
        if (!isConnected)
            return;

        this.id = clientID;

        try {
            wrappedClient.setID(clientID);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {setID(clientID);});
        }
    }

    @Override
    public void showWaitingLobbies(int clientID, List<Lobby> lobbies)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.showWaitingLobbies(clientID, lobbies);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {showWaitingLobbies(clientID, lobbies);});
        }
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players)  {
        if (!isConnected)
            return;

        currLobbyID = lobbyID;

        try {
            wrappedClient.showLobbyInfo(clientID, lobbyID, players);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {showLobbyInfo(clientID, lobbyID, players);});
        }
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player)  {
        if (!isConnected)
            return;

        if (clientID == this.id) currLobbyID = lobbyID;

        try {
            wrappedClient.setLobby(clientID, lobbyID, player);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {setLobby(clientID, lobbyID, player);});
        }
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID)  {
        if (!isConnected)
            return;

        if (clientID == this.id) currLobbyID = -1;

        try {
            wrappedClient.removeFromLobby(clientID, lobbyID);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {removeFromLobby(clientID, lobbyID);});
        }
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.showRank(clientID, lobbyID, rankings);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {showRank(clientID, lobbyID, rankings);});
        }
    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.showLeaderboard(clientID, leaderboard);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {showLeaderboard(clientID, leaderboard);});
        }
    }

    @Override
    public void updateModel(int clientID, Board board, Tribe updatedTribe)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.updateModel(clientID, board, updatedTribe);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {
                updateModel(clientID, board, updatedTribe);});
        }
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player)  {
        if (!isConnected)
            return;

        currLobbyID = lobby.getLobbyID();

        try {
            wrappedClient.createLobby(clientID, lobby, player);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {createLobby(clientID, lobby, player);});
        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes)  {
        if (!isConnected)
            return;

        try {
            wrappedClient.startLobby(clientID, lobbyID, board, tribes);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {startLobby(clientID, lobbyID, board, tribes);});
        }
    }

    @Override
    public void stopLobby(int lobbyID)  {
        if (!isConnected)
            return;

        if (currLobbyID == lobbyID) currLobbyID = -1;

        try {
            wrappedClient.stopLobby(lobbyID);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {stopLobby(lobbyID);});
        }
    }

    @Override
    public void deleteLobby(int lobbyID)  {
        if (!isConnected)
            return;

        if (currLobbyID == lobbyID) currLobbyID = -1;

        try {
            wrappedClient.deleteLobby(lobbyID);
        } catch (IOException e) {
            ServerController.getInstance().scheduleRetry(() -> {deleteLobby(lobbyID);});
        }
    }

    @Override
    public void ping() throws IOException, RemoteException {
        wrappedClient.ping();
    }
}
