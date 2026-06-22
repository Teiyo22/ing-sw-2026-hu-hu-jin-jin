package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.leaderboard.LeaderboardResult;

import java.util.List;
import java.util.Set;

class TestClient extends ClientInterface {
    private String id;
    private LobbyController currentLobby;
    private boolean loginConfirmed = false;

    public TestClient(String id) {
        this.id = id;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setCurrLobbyController(LobbyController l) {
        this.currentLobby = l;
    }

    @Override
    public LobbyController getCurrLobbyController() {
        return currentLobby;
    }

    @Override
    public void showEventResults(EventResultMessage eventResultMessage) {

    }

    @Override
    public void updateModel(int lobbyID, Player player, int offerIndex) {

    }

    @Override
    public void updateModel(int lobbyID, Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {

    }

    @Override
    public void updateModel(int lobbyID, List<Player> players, Row topRow, boolean eraChanged) {

    }

    @Override
    public void updateModel(int lobbyID, List<Player> players) {

    }

    @Override
    public void ping() {

    }

    @Override
    public void createLobby(Lobby l, Player p) {
    }

    @Override
    public void startLobby(int lobbyID, Board board, List<Player> players) {
    }

    @Override
    public void stopLobby(int lobbyID) {
    }

    @Override
    public void showError(ErrorMessage errorMsg) {
    }

    @Override
    public void confirmLogin(String username) {
        this.loginConfirmed = true;
    }

    public boolean isLoginConfirmed() {
        return loginConfirmed;
    }

    @Override
    public void showWaitingLobbies(List<Lobby> lobbies) {
    }

    @Override
    public void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {
    }

    @Override
    public void addPlayer(int lobbyID, Player player) {
    }

    @Override
    public void addLobby(Lobby lobby) {
    }

    @Override
    public void removeLobby(int lobbyID) {
    }

    @Override
    public void updateLobby(Lobby l) {
    }

    @Override
    public void removeClient(int lobbyID, Player player) {
    }

    @Override
    public void removePlayer(int lobbyID, Player player) {
    }

    @Override
    public void showLeaderboard(LeaderboardResult leaderboardResult) {
    }

    @Override
    public void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
    }
}
