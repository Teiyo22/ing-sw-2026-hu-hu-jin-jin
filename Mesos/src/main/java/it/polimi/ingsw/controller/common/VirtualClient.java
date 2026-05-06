package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface VirtualClient extends Remote {
    void setID(int clientID) throws RemoteException;
    void showWaitingLobbies(int clientID, List<Lobby> lobbies) throws RemoteException;
    void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) throws RemoteException;
    void addToLobby(int clientID, int lobbyID, Player player) throws RemoteException;
    void removeFromLobby(int clientID, int lobbyID) throws RemoteException;
    void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) throws RemoteException;
    void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) throws RemoteException;
    void updateModel(int clientID, Board board, Tribe updatedTribe) throws RemoteException;
    void createLobby(int clientID, Lobby lobby, Player player) throws RemoteException;
    void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) throws RemoteException;
    void removeLobby(int lobbyID) throws RemoteException;
    void showError(int clientID, String errorMessage) throws RemoteException;

    void ping() throws RemoteException;
}