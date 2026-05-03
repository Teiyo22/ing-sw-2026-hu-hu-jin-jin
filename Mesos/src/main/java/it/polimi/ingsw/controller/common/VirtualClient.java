package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public abstract class VirtualClient implements Remote {
    protected int id;

    public void setID(int clientID) throws RemoteException {
        this.id = clientID;
    }

    public abstract void showWaitingLobbies(int clientID, List<Lobby> lobbies) throws RemoteException;
    public abstract void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) throws RemoteException;
    public abstract void addToLobby(int clientID, int lobbyID, Player player) throws RemoteException;
    public abstract void removeFromLobby(int clientID, int lobbyID) throws RemoteException;
    public abstract void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) throws RemoteException;
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) throws RemoteException;
    public abstract void updateModel(int clientID, Board board, Tribe updatedTribe) throws RemoteException;
    public abstract void createLobby(int clientID, Lobby lobby, Player player) throws RemoteException;
    public abstract void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) throws RemoteException;
    public abstract void stopLobby(int lobbyID) throws RemoteException;
    public abstract void removeLobby(int lobbyID) throws RemoteException;
    public abstract void ping() throws IOException, RemoteException;
    public abstract void handleError(int clientID, String errorMessage);
}