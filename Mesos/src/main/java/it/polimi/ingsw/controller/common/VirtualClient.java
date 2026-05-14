package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface VirtualClient extends Remote {
    void setID(String clientID) throws RemoteException;
    void confirmLogin(String username) throws RemoteException;
    void showWaitingLobbies(List<Lobby> lobbies) throws RemoteException;
    void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) throws RemoteException;
    void addPlayer(int lobbyID, Player player) throws RemoteException;
    void addLobby(Lobby lobby) throws RemoteException;
    void removeLobby(int lobbyID) throws RemoteException;
    void updateLobby(Lobby lobby) throws RemoteException;
    void removeClient(int lobbyID, Player player) throws RemoteException;
    void removePlayer(int lobbyID, Player player) throws RemoteException;
    void showLeaderboard(List<LeaderboardEntry> leaderboard) throws RemoteException;
    void updateState(int lobbyID, ModelStateInfo modelStateInfo) throws RemoteException;
    void createLobby(Lobby lobby, Player player) throws RemoteException;
    void startLobby(int lobbyID, Board board, Collection<Player> players) throws RemoteException;
    void stopLobby(int lobbyID) throws RemoteException;
    void showError(String errorMessage) throws RemoteException;

    void updateModel(int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) throws RemoteException;
    void updateModel(int lobbyID, Player player, Board board) throws RemoteException;
    void updateModel(int lobbyID, Player player, Row topRow) throws RemoteException;
    void updateModel(int lobbyID, Collection<Player> players, Row topRow, Row bottomRow) throws RemoteException;
    void updateModel(int lobbyID, Collection<Player> players) throws RemoteException;

    void ping() throws RemoteException;
}