package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.leaderboard.LeaderboardResult;
import it.polimi.ingsw.utils.logger.Logger;
import it.polimi.ingsw.utils.logger.LoggerLevel;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public class RMIClientInterface extends ClientInterface implements Serializable {
    private VirtualClient wrappedClient;

    public RMIClientInterface(VirtualClient wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    @Override
    public void setID(String clientID) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to setID");
        this.id = clientID;

        submitRemoteCall(
                () -> wrappedClient.setID(clientID)
        );
    }

    @Override
    public void confirmLogin(String username) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to confirmLogin");
        this.id = username;

        submitRemoteCall(
                () -> wrappedClient.confirmLogin(username)
        );
    }

    @Override
    public void showWaitingLobbies(List<Lobby> lobbies) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showWaitingLobbies");
        submitRemoteCall(
                () -> wrappedClient.showWaitingLobbies(lobbies)
        );
    }

    @Override
    public void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showLobbyInfo");
       submitRemoteCall(
               () -> wrappedClient.showLobbyInfo(lobbyID, connectedPlayers, disconnectedPlayers)
       );
    }

    @Override
    public void addPlayer(int lobbyID, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to addPlayer");
        submitRemoteCall(
                () -> wrappedClient.addPlayer(lobbyID, player)
        );
    }

    @Override
    public void addLobby(Lobby lobby) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to addLobby");
        submitRemoteCall(
                () -> wrappedClient.addLobby(lobby)
        );
    }

    @Override
    public void removeLobby(int lobbyID) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to removeLobby");
        if (currLobbyController.getID() == lobbyID)
            currLobbyController = null;

        submitRemoteCall(
                () -> wrappedClient.removeLobby(lobbyID)
        );
    }

    @Override
    public void updateLobby(Lobby lobby) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateLobby");
        submitRemoteCall(
                () -> wrappedClient.updateLobby(lobby)
        );
    }

    @Override
    public void removeClient(int lobbyID, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to removeClient");
        submitRemoteCall(
                () -> wrappedClient.removeClient(lobbyID, player)
        );
    }

    @Override
    public void removePlayer(int lobbyID, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to removePlayer");
        submitRemoteCall(
                () -> wrappedClient.removeClient(lobbyID, player)
        );
    }

    @Override
    public void showLeaderboard(LeaderboardResult leaderboard) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showLeaderboard");
        submitRemoteCall(
                () -> wrappedClient.showLeaderboard(leaderboard)
        );
    }

    @Override
    public void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateState");
        submitRemoteCall(
                () -> wrappedClient.updateState(lobbyID, modelStateInfo)
        );
    }

    @Override
    public void updateModel(int lobbyID, Player player, int offerIndex) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, player, offerIndex)
        );
    }

    @Override
    public void updateModel(int lobbyID, Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, player, topRowPicks, bottomRowPicks)
        );
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players, Row topRow, boolean eraChanged) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, players, topRow, eraChanged)
        );
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to updateModel");
        submitRemoteCall(
                () -> wrappedClient.updateModel(lobbyID, players)
        );
    }

    @Override
    public void createLobby(Lobby lobby, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to createLobby");
        submitRemoteCall(
                () -> wrappedClient.createLobby(lobby, player)
        );
    }

    @Override
    public void startLobby(int lobbyID, Board board, List<Player> players) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to startLobby");
        submitRemoteCall(
                () -> wrappedClient.startLobby(lobbyID, board, players)
        );
    }

    @Override
    public void stopLobby(int lobbyID) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to stopLobby");
        submitRemoteCall(
                () -> wrappedClient.stopLobby(lobbyID)
        );
    }

    @Override
    public void showError(ErrorMessage errorMsg) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showError");
        submitRemoteCall(
                () -> wrappedClient.showError(errorMsg)
        );
    }

    @Override
    public void showEventResults(EventResultMessage eventResultMessage) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Remote call to showEventResults");
        submitRemoteCall(
                () -> wrappedClient.showEventResults(eventResultMessage)
        );
    }

    @Override
    public void ping() {
        submitRemoteCall(
                () -> wrappedClient.ping()
        );
    }

    @FunctionalInterface
    interface RunnableChecked {
        void run() throws RemoteException;
    }

    private void submitRemoteCall(RunnableChecked remoteCall) {
        if (!isConnected) return;
        ServerController.getInstance().submitResponse(() -> {
            try {
                remoteCall.run();
            } catch (RemoteException e)  {
                ServerController.getInstance().disconnectClient(this);
            }
        });
    }
}
