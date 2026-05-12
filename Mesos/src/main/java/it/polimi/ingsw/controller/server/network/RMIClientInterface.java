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
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class RMIClientInterface extends ClientInterface {
    VirtualClient wrappedClient;

    public RMIClientInterface(VirtualClient wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    @Override
    public synchronized void setID(int clientID) {
        this.id = clientID;

        submitRemoteCall(
                () -> wrappedClient.setID(clientID),
                () -> this.setID(clientID)
        );
    }

    @Override
    public synchronized void showWaitingLobbies(int clientID, List<Lobby> lobbies) {
        submitRemoteCall(
                () -> wrappedClient.showWaitingLobbies(clientID, lobbies),
                () -> this.showWaitingLobbies(clientID, lobbies)
        );
    }

    @Override
    public synchronized void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        setCurrLobbyController(lobbyID);

       submitRemoteCall(
               () -> wrappedClient.showLobbyInfo(clientID, lobbyID, players),
               () -> this.showLobbyInfo(clientID, lobbyID, players)
       );
    }

    @Override
    public synchronized void addClient(int clientID, int lobbyID, Player player) {
        submitRemoteCall(
                () -> wrappedClient.addClient(clientID, lobbyID, player),
                () -> this.addClient(clientID, lobbyID, player)
        );
    }

    @Override
    public synchronized void addPlayer(int clientID, int lobbyID, Player player) {
        submitRemoteCall(
                () -> wrappedClient.addPlayer(clientID, lobbyID, player),
                () -> this.addPlayer(clientID, lobbyID, player)
        );
    }

    @Override
    public synchronized void removeClient(int clientID, int lobbyID, Player player) {
        submitRemoteCall(
                () -> wrappedClient.removeClient(clientID, lobbyID, player),
                () -> this.removeClient(clientID, lobbyID, player)
        );
    }

    @Override
    public synchronized void removePlayer(int clientID, int lobbyID, Player player) {
        submitRemoteCall(
                () -> wrappedClient.removeClient(clientID, lobbyID, player),
                () -> this.removePlayer(clientID, lobbyID, player)
        );
    }

    @Override
    public synchronized void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {
        submitRemoteCall(
                () -> wrappedClient.showLeaderboard(clientID, leaderboard),
                () -> this.showLeaderboard(clientID, leaderboard)
        );
    }

    @Override
    public synchronized void updateState(int clientID, int lobbyID, ModelStateInfo modelStateInfo) {
        submitRemoteCall(
                () -> wrappedClient.updateState(clientID, lobbyID, modelStateInfo),
                () -> this.updateState(clientID, lobbyID, modelStateInfo)
        );
    }

    @Override
    public void updateModel(int clientID, int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(clientID, lobbyID, orderTile, offerTrack),
                () -> this.updateModel(clientID, lobbyID, orderTile, offerTrack)
        );
    }

    @Override
    public void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Board board) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(clientID, lobbyID, player, tribe, board),
                () -> this.updateModel(clientID, lobbyID, player, tribe, board)
        );
    }

    @Override
    public void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Row topRow) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(clientID, lobbyID, player, tribe, topRow),
                () -> this.updateModel(clientID, lobbyID, player, tribe, topRow)
        );
    }

    @Override
    public void updateModel(int clientID, int lobbyID, Map<Integer, Tribe> tribes, Row topRow, Row bottomRow) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(clientID, lobbyID, tribes, topRow, bottomRow),
                () -> this.updateModel(clientID, lobbyID, tribes, topRow, bottomRow)
        );
    }

    @Override
    public void updateModel(int clientID, int lobbyID, Map<Integer, Tribe> tribes, Map<Integer, Integer> ranking) {
        submitRemoteCall(
                () -> wrappedClient.updateModel(clientID, lobbyID, tribes, ranking),
                () -> this.updateModel(clientID, lobbyID, tribes, ranking)
        );
    }

    @Override
    public synchronized void createLobby(int clientID, Lobby lobby, Player player) {
        submitRemoteCall(
                () -> wrappedClient.createLobby(clientID, lobby, player),
                () -> this.createLobby(clientID, lobby, player)
        );
    }

    @Override
    public synchronized void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        submitRemoteCall(
                () -> wrappedClient.startLobby(clientID, lobbyID, board, tribes),
                () -> this.startLobby(clientID, lobbyID, board, tribes)
        );
    }

    @Override
    public synchronized void stopLobby(int clientID, int lobbyID) {
        submitRemoteCall(
                () -> wrappedClient.stopLobby(clientID, lobbyID),
                () -> this.stopLobby(clientID, lobbyID)
        );
    }

    @Override
    public synchronized void showError(int clientID, String errorMessage) {
        submitRemoteCall(
                () -> wrappedClient.showError(clientID, errorMessage),
                () -> this.showError(clientID, errorMessage)
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
