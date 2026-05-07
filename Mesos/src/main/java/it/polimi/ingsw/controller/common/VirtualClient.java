package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface VirtualClient extends Remote {
    void setID(int clientID) throws RemoteException;
    void showWaitingLobbies(int clientID, List<Lobby> lobbies) throws RemoteException;
    void showLobbyInfo(int clientID, int lobbyID, Map<Player, Integer> players) throws RemoteException;
    void addClient(int clientID, int lobbyID, Player player) throws RemoteException;
    void addPlayer(int clientID, int lobbyID, Player player) throws RemoteException;
    void removeClient(int clientID, int lobbyID, Player player) throws RemoteException;
    void removePlayer(int clientID, int lobbyID, Player player) throws RemoteException;
    void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) throws RemoteException;
    void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) throws RemoteException;
    void updateModel(int clientID, Board board, Tribe updatedTribe) throws RemoteException;
    void createLobby(int clientID, Lobby lobby, Player player) throws RemoteException;
    void startLobby(int clientID, int lobbyID, Board board, Map<Player, Tribe> tribes) throws RemoteException;
    void stopLobby(int clientID, int lobbyID) throws RemoteException;
    void showError(int clientID, String errorMessage) throws RemoteException;

    void ping() throws RemoteException;
}