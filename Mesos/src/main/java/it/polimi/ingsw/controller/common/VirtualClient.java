package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.leaderboard.LeaderboardResult;

import java.rmi.Remote;
import java.rmi.RemoteException;
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
    void showLeaderboard(LeaderboardResult leaderboardResult) throws RemoteException;
    void updateState(int lobbyID, ModelStateInfo modelStateInfo) throws RemoteException;
    void createLobby(Lobby lobby, Player player) throws RemoteException;
    void startLobby(int lobbyID, Board board, List<Player> players) throws RemoteException;
    void stopLobby(int lobbyID) throws RemoteException;
    void showError(ErrorMessage errorMsg) throws RemoteException;
    void showEventResults(EventResultMessage eventResultMessage) throws RemoteException;

    void updateModel(int lobbyID, Player player, int offerIndex) throws RemoteException;
    void updateModel(int lobbyID, Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) throws RemoteException;
    void updateModel(int lobbyID, List<Player> players, Row topRow, boolean eraChanged) throws RemoteException;
    void updateModel(int lobbyID, List<Player> players) throws RemoteException;

    void ping() throws RemoteException;
}