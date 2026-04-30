package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Tribe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public abstract class VirtualClient implements Remote {
    protected int id;

    public void setID(int clientID) throws RemoteException {
        this.id = clientID;
    }
    public int getID() throws RemoteException {
        return id;
    }
    public abstract void setWaitingLobbies(int clientID, List<Lobby> lobbies) throws RemoteException;
    public abstract void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) throws RemoteException;
    public abstract void setLobby(int clientID, int lobbyID, Player player) throws RemoteException;
    public abstract void removeFromLobby(int clientID, int lobbyID) throws RemoteException;
    public abstract void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) throws RemoteException;
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) throws RemoteException;
    public abstract void confirmPick(int clientID, Board board, Tribe updatedTribe) throws RemoteException;
    public abstract void createLobby(int clientID, Lobby lobby, Player player) throws RemoteException;
    public abstract void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) throws RemoteException;
    public abstract void terminateLobby(int lobbyID) throws RemoteException;
    public abstract void deleteLobby(int lobbyID) throws RemoteException;
    public abstract void ping() throws Exception;
}