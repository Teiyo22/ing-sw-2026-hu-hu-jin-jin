package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public abstract class ClientInterface extends VirtualClient {
    protected int currLobbyID = 0;
    protected boolean isConnected = false;

    public abstract void setID(int clientID);
    public abstract void showWaitingLobbies(int clientID, List<Lobby> lobbies);
    public abstract void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players);
    public abstract void setLobby(int clientID, int lobbyID, Player player);
    public abstract void removeFromLobby(int clientID, int lobbyID);
    public abstract void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings);
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard);
    public abstract void updateModel(int clientID, Board board, Tribe updatedTribe);
    public abstract void createLobby(int clientID, Lobby lobby, Player player);
    public abstract void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes);
    public abstract void stopLobby(int lobbyID);
    public abstract void deleteLobby(int lobbyID);
    public abstract void ping() throws IOException, RemoteException;

    public int getID() {
        return id;
    }

    public int getCurrLobbyID() {
        return currLobbyID;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
