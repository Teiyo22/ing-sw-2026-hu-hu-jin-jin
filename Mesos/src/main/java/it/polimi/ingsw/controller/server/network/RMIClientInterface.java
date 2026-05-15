package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public class RMIClientInterface extends ClientInterface implements Serializable {
    VirtualClient wrappedClient;

    public RMIClientInterface(VirtualClient wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    @Override
    public synchronized void setID(String clientID) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to setID");
        this.id = clientID;

        submitRemoteCall(
                () -> wrappedClient.setID(clientID),
                () -> this.setID(clientID)
        );
    }

    @Override
    public void confirmLogin(String username) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to confirmLogin");
        this.id = username;

        submitRemoteCall(
                () -> wrappedClient.confirmLogin(username),
                () -> this.confirmLogin(username)
        );
    }

    @Override
    public synchronized void showWaitingLobbies(List<Lobby> lobbies) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showWaitingLobbies");
        submitRemoteCall(
                () -> wrappedClient.showWaitingLobbies(lobbies),
                () -> this.showWaitingLobbies(lobbies)
        );
    }

    @Override
    public synchronized void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showLobbyInfo");
       submitRemoteCall(
               () -> wrappedClient.showLobbyInfo(lobbyID, connectedPlayers, disconnectedPlayers),
               () -> this.showLobbyInfo(lobbyID, connectedPlayers, disconnectedPlayers)
       );
    }

    @Override
    public synchronized void addPlayer(int lobbyID, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to addPlayer");
        submitRemoteCall(
                () -> wrappedClient.addPlayer(lobbyID, player),
                () -> this.addPlayer(lobbyID, player)
        );
    }

    @Override
    public void addLobby(Lobby lobby) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to addLobby");
        submitRemoteCall(
                () -> wrappedClient.addLobby(lobby),
                () -> this.addLobby(lobby)
        );
    }

    @Override
    public void removeLobby(int lobbyID) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to removeLobby");
        if (currLobbyController.getID() == lobbyID)
            currLobbyController = null;

        submitRemoteCall(
                () -> wrappedClient.removeLobby(lobbyID),
                () -> this.removeLobby(lobbyID)
        );
    }

    @Override
    public void updateLobby(Lobby lobby) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateLobby");
        submitRemoteCall(
                () -> wrappedClient.updateLobby(lobby),
                () -> this.updateLobby(lobby)
        );
    }

    @Override
    public synchronized void removeClient(int lobbyID, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to removeClient");
        submitRemoteCall(
                () -> wrappedClient.removeClient(lobbyID, player),
                () -> this.removeClient(lobbyID, player)
        );
    }

    @Override
    public synchronized void removePlayer(int lobbyID, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to removePlayer");
        submitRemoteCall(
                () -> wrappedClient.removeClient(lobbyID, player),
                () -> this.removePlayer(lobbyID, player)
        );
    }

    @Override
    public synchronized void showLeaderboard(List<LeaderboardEntry> leaderboard) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showLeaderboard");
        submitRemoteCall(
                () -> wrappedClient.showLeaderboard(leaderboard),
                () -> this.showLeaderboard(leaderboard)
        );
    }

    @Override
    public synchronized void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateState");
        submitRemoteCall(
                () -> wrappedClient.updateState(lobbyID, modelStateInfo),
                () -> this.updateState(lobbyID, modelStateInfo)
        );
    }

    @Override
    public void updateModel(int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, orderTile, offerTrack),
                () -> this.updateModel(lobbyID, orderTile, offerTrack)
        );
    }

    @Override
    public void updateModel(int lobbyID, Player player, Board board) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, player, board),
                () -> this.updateModel(lobbyID, player, board)
        );
    }

    @Override
    public void updateModel(int lobbyID, Player player, Row topRow) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, player, topRow),
                () -> this.updateModel(lobbyID, player, topRow)
        );
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players, Row topRow, Row bottomRow) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, players, topRow, bottomRow),
                () -> this.updateModel(lobbyID, players, topRow, bottomRow)
        );
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, players),
                () -> this.updateModel(lobbyID, players)
        );
    }

    @Override
    public synchronized void createLobby(Lobby lobby, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to createLobby");
        submitRemoteCall(
                () -> wrappedClient.createLobby(lobby, player),
                () -> this.createLobby(lobby, player)
        );
    }

    @Override
    public synchronized void startLobby(int lobbyID, Board board, List<Player> players) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to startLobby");
        submitRemoteCall(
                () -> wrappedClient.startLobby(lobbyID, board, players),
                () -> this.startLobby(lobbyID, board, players)
        );
    }

    @Override
    public synchronized void stopLobby(int lobbyID) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to stopLobby");
        submitRemoteCall(
                () -> wrappedClient.stopLobby(lobbyID),
                () -> this.stopLobby(lobbyID)
        );
    }

    @Override
    public synchronized void showError(String errorMessage) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showError");
        submitRemoteCall(
                () -> wrappedClient.showError(errorMessage),
                () -> this.showError(errorMessage)
        );
    }

    @Override
    public void ping() {
        ServerController.getInstance().submitResponse(() -> {
            try {
                wrappedClient.ping();
            } catch (RemoteException ignore) { }
        });
    }

    @FunctionalInterface
    interface RunnableChecked {
        void run() throws RemoteException;
    }

    private void submitRemoteCall(RunnableChecked remoteCall, Runnable retryAction) {
        if (!isConnected) return;
        ServerController.getInstance().submitResponse(() -> {
            try {
                remoteCall.run();
            } catch (Exception e)  {
                ServerController.getInstance().scheduleRetry(retryAction);
            }
        });
    }
}
