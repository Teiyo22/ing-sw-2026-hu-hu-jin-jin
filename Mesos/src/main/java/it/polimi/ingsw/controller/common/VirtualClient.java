package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VirtualClient extends Remote {
    void setID(String clientID) throws RemoteException;
    void confirmLogin(String username) throws RemoteException;
    void showWaitingLobbies(List<Lobby> lobbies) throws RemoteException;
    void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) throws RemoteException;
    void addPlayer(int lobbyID, Player player) throws RemoteException;
    void removeClient(int lobbyID, Player player) throws RemoteException;
    void removePlayer(int lobbyID, Player player) throws RemoteException;
    void showLeaderboard(List<LeaderboardEntry> leaderboard) throws RemoteException;
    void updateState(int lobbyID, ModelStateInfo modelStateInfo) throws RemoteException;
    void createLobby(Lobby lobby, Player player) throws RemoteException;
    void startLobby(int lobbyID, Board board, Map<String, Tribe> tribes) throws RemoteException;
    void stopLobby(int lobbyID) throws RemoteException;
    void showError(String errorMessage) throws RemoteException;

    void updateModel(int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) throws RemoteException;
    void updateModel(int lobbyID, Player player, Tribe tribe, Board board) throws RemoteException;
    void updateModel(int lobbyID, Player player, Tribe tribe, Row topRow) throws RemoteException;
    void updateModel(int lobbyID, Map<String, Tribe> tribes, Row topRow, Row bottomRow) throws RemoteException;
    void updateModel(int lobbyID, Map<String, Tribe> tribes, Map<String, Integer> ranking) throws RemoteException;

    void ping() throws RemoteException;
}