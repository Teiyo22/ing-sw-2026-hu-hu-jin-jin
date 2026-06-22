package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.leaderboard.LeaderboardResult;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * RMIClientService acts as the entry-point for the RMI remote method calls from the server to the client.
 * Each method essentially submits a task to an executor service to avoid long-lasting blocking calls on the server-side.
 * */
public class RMIClientService extends ClientInterface implements Serializable {
    private final ClientController clientController;

    public RMIClientService(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void setID(String clientID) {
        clientController.submitCPUTask(() ->
            clientController.setID(clientID)
        );
    }

    @Override
    public void confirmLogin(String username) {
        clientController.submitCPUTask(() ->
            clientController.confirmLogin(username)
        );
    }

    @Override
    public void showWaitingLobbies(List<Lobby> lobbies) {
        clientController.submitCPUTask(() ->
            clientController.showWaitingLobbies(lobbies)
        );
    }

    @Override
    public void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {
        clientController.submitCPUTask(() ->
            clientController.showLobbyInfo(lobbyID, connectedPlayers, disconnectedPlayers)
        );
    }

    @Override
    public void addPlayer(int lobbyID, Player player) {
        clientController.submitCPUTask(() ->
            clientController.addPlayer(lobbyID, player)
        );
    }

    @Override
    public void addLobby(Lobby lobby) {
        clientController.submitCPUTask(() ->
            clientController.addLobby(lobby));

    }

    @Override
    public void createLobby(Lobby lobby, Player player) {
        clientController.submitCPUTask(() ->
            clientController.createLobby(lobby, player));
    }

    @Override
    public void removeLobby(int lobbyID) {
        clientController.submitCPUTask(() ->
            clientController.removeLobby(lobbyID));

    }

    @Override
    public void updateLobby(Lobby lobby) {
        clientController.submitCPUTask(() ->
            clientController.updateLobby(lobby))
        ;
    }

    @Override
    public void removeClient(int lobbyID, Player player) {
        clientController.submitCPUTask(() ->
            clientController.removeClient(lobbyID, player)
        );
    }

    @Override
    public void removePlayer(int lobbyID, Player player) {
        clientController.submitCPUTask(() ->
            clientController.removePlayer(lobbyID, player)
        );
    }

    @Override
    public void showLeaderboard(LeaderboardResult leaderboardResult) {
        clientController.submitCPUTask(() ->
            clientController.showLeaderboard(leaderboardResult)
        );
    }

    @Override
    public void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
        clientController.submitCPUTask(() ->
            clientController.updateState(lobbyID, modelStateInfo)
        );
    }

    @Override
    public void startLobby(int lobbyID, Board board, List<Player> players) {
        clientController.submitCPUTask(() ->
            clientController.startLobby(lobbyID, board, players)
        );
    }

    @Override
    public void stopLobby(int lobbyID) {
        clientController.submitCPUTask(() ->
            clientController.stopLobby(lobbyID)
        );
    }

    @Override
    public void showError(ErrorMessage errorMsg) {
        clientController.submitCPUTask(() ->
            clientController.showError(errorMsg)
        );
    }

    @Override
    public void showEventResults(EventResultMessage eventResultMessage) {
        clientController.submitCPUTask(() ->
            clientController.showEventResults(eventResultMessage)
        );
    }

    @Override
    public void updateModel(int lobbyID, Player player, int offerIndex) {
        clientController.submitCPUTask(() ->
            clientController.updateModel(lobbyID, player, offerIndex)
        );
    }

    @Override
    public void updateModel(int lobbyID, Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        clientController.submitCPUTask(() ->
            clientController.updateModel(lobbyID, player, topRowPicks, bottomRowPicks)
        );
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players, Row topRow, boolean eraChanged) {
        clientController.submitCPUTask(() ->
            clientController.updateModel(lobbyID, players, topRow, eraChanged)
        );
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players) {
        clientController.submitCPUTask(() ->
            clientController.updateModel(lobbyID, players)
        );
    }

    @Override
    public void disconnect() {
        clientController.submitCPUTask(clientController::disconnect);
    }

    @Override
    public void ping() {
        clientController.ping();
    }
}
