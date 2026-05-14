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

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RMIClientInterface extends ClientInterface implements Serializable {
    VirtualClient wrappedClient;

    public RMIClientInterface(VirtualClient wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    @Override
    public synchronized void setID(String clientID) {
        this.id = clientID;

        submitRemoteCall(
                () -> wrappedClient.setID(clientID),
                () -> this.setID(clientID)
        );
    }

    @Override
    public void confirmLogin(String username) {
        this.id = username;

        submitRemoteCall(
                () -> wrappedClient.confirmLogin(username),
                () -> this.confirmLogin(username)
        );
    }

    @Override
    public synchronized void showWaitingLobbies(List<Lobby> lobbies) {
        submitRemoteCall(
                () -> wrappedClient.showWaitingLobbies(lobbies),
                () -> this.showWaitingLobbies(lobbies)
        );
    }

    @Override
    public synchronized void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {
       submitRemoteCall(
               () -> wrappedClient.showLobbyInfo(lobbyID, connectedPlayers, disconnectedPlayers),
               () -> this.showLobbyInfo(lobbyID, connectedPlayers, disconnectedPlayers)
       );
    }

    @Override
    public synchronized void addPlayer(int lobbyID, Player player) {
        submitRemoteCall(
                () -> wrappedClient.addPlayer(lobbyID, player),
                () -> this.addPlayer(lobbyID, player)
        );
    }

    @Override
    public void addLobby(Lobby lobby) {
        submitRemoteCall(
                () -> wrappedClient.addLobby(lobby),
                () -> this.addLobby(lobby)
        );
    }

    @Override
    public void removeLobby(int lobbyID) {
        if (currLobbyController.getID() == lobbyID)
            currLobbyController = null;

        submitRemoteCall(
                () -> wrappedClient.removeLobby(lobbyID),
                () -> this.removeLobby(lobbyID)
        );
    }

    @Override
    public void updateLobby(Lobby lobby) {
        submitRemoteCall(
                () -> wrappedClient.updateLobby(lobby),
                () -> this.updateLobby(lobby)
        );
    }

    @Override
    public synchronized void removeClient(int lobbyID, Player player) {
        submitRemoteCall(
                () -> wrappedClient.removeClient(lobbyID, player),
                () -> this.removeClient(lobbyID, player)
        );
    }

    @Override
    public synchronized void removePlayer(int lobbyID, Player player) {
        submitRemoteCall(
                () -> wrappedClient.removeClient(lobbyID, player),
                () -> this.removePlayer(lobbyID, player)
        );
    }

    @Override
    public synchronized void showLeaderboard(List<LeaderboardEntry> leaderboard) {
        submitRemoteCall(
                () -> wrappedClient.showLeaderboard(leaderboard),
                () -> this.showLeaderboard(leaderboard)
        );
    }

    @Override
    public synchronized void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
        submitRemoteCall(
                () -> wrappedClient.updateState(lobbyID, modelStateInfo),
                () -> this.updateState(lobbyID, modelStateInfo)
        );
    }

    @Override
    public void updateModel(int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, orderTile, offerTrack),
                () -> this.updateModel(lobbyID, orderTile, offerTrack)
        );
    }

    @Override
    public void updateModel(int lobbyID, Player player, Board board) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, player, board),
                () -> this.updateModel(lobbyID, player, board)
        );
    }

    @Override
    public void updateModel(int lobbyID, Player player, Row topRow) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, player, topRow),
                () -> this.updateModel(lobbyID, player, topRow)
        );
    }

    @Override
    public void updateModel(int lobbyID, Collection<Player> players, Row topRow, Row bottomRow) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, players, topRow, bottomRow),
                () -> this.updateModel(lobbyID, players, topRow, bottomRow)
        );
    }

    @Override
    public void updateModel(int lobbyID, Collection<Player> players) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, players),
                () -> this.updateModel(lobbyID, players)
        );
    }

    @Override
    public synchronized void createLobby(Lobby lobby, Player player) {
        submitRemoteCall(
                () -> wrappedClient.createLobby(lobby, player),
                () -> this.createLobby(lobby, player)
        );
    }

    @Override
    public synchronized void startLobby(int lobbyID, Board board, Collection<Player> players) {
        submitRemoteCall(
                () -> wrappedClient.startLobby(lobbyID, board, players),
                () -> this.startLobby(lobbyID, board, players)
        );
    }

    @Override
    public synchronized void stopLobby(int lobbyID) {
        submitRemoteCall(
                () -> wrappedClient.stopLobby(lobbyID),
                () -> this.stopLobby(lobbyID)
        );
    }

    @Override
    public synchronized void showError(String errorMessage) {
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
