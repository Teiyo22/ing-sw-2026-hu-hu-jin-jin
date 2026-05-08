package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.info.ModelStateInfo;
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

public abstract class ClientInterface implements VirtualClient {
    protected int id = 0;
    protected int currLobbyID = 0;
    protected boolean isConnected = false;


    public abstract void showWaitingLobbies(int clientID, List<Lobby> lobbies);
    public abstract void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players);
    public abstract void addToLobby(int clientID, int lobbyID, Player player);
    public abstract void removeFromLobby(int clientID, int lobbyID);
    public abstract void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings);
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard);
    public abstract void updateModel(int clientID, Board board, Tribe updatedTribe);
    public abstract void updateState(int clientID, ModelStateInfo modelStateInfo);
    public abstract void createLobby(int clientID, Lobby lobby, Player player);
    public abstract void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes);
    public abstract void removeLobby(int lobbyID);
    public abstract void ping() throws IOException, RemoteException;
    public void cleanup() {};
    public abstract void showError(int clientID, String errorMessage);


    public int getID() {
        return id;
    }

    public void setID(int clientID) {
        id = clientID;
    };

    public int getCurrLobbyID() {
        return currLobbyID;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
