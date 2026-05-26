package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.common.messages.responses.*;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Set;

public class TCPClientInterface extends ClientInterface {
    private ClientHandler clientHandler;

    public TCPClientInterface(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void handleMessage(Request request) {
        request.receive(ServerController.getInstance());
    }

    @Override
    public void setID(String clientID) {
        this.id = clientID;

        SetIDResponse response = new SetIDResponse(clientID);
        sendMessage(response);
    }

    @Override
    public void confirmLogin(String username) {
        this.id = username;

        LoginResponse response = new LoginResponse(username);
        sendMessage(response);
    }

    @Override
    public void showWaitingLobbies(List<Lobby> lobbies) {
        WaitingLobbyResponse response = new WaitingLobbyResponse(lobbies);
        sendMessage(response);
    }

    @Override
    public void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {
        LobbyInfoResponse response = new LobbyInfoResponse(lobbyID, connectedPlayers, disconnectedPlayers);
        sendMessage(response);
    }

    @Override
    public void addPlayer(int lobbyID, Player player) {
        AddPlayerResponse response = new AddPlayerResponse(lobbyID, player);
        sendMessage(response);
    }

    @Override
    public void addLobby(Lobby lobby) {
        AddLobbyResponse response = new AddLobbyResponse(lobby);
        sendMessage(response);
    }

    @Override
    public void removeLobby(int lobbyID) {
        if (currLobbyController.getID() == lobbyID)
            currLobbyController = null;

        RemoveLobbyResponse response = new RemoveLobbyResponse(lobbyID);
        sendMessage(response);
    }

    @Override
    public void updateLobby(Lobby lobby) {
        UpdateLobbyResponse response = new UpdateLobbyResponse(lobby);
        sendMessage(response);
    }

    @Override
    public void removeClient(int lobbyID, Player player) {
        RemoveClientResponse response = new RemoveClientResponse(lobbyID, player);
        sendMessage(response);
    }

    @Override
    public void removePlayer(int lobbyID, Player player) {
        RemovePlayerResponse response = new RemovePlayerResponse(lobbyID, player);
        sendMessage(response);
    }

    @Override
    public void showLeaderboard(List<LeaderboardEntry> leaderboard) {
        GetLeaderboardResponse response = new GetLeaderboardResponse(leaderboard);
        sendMessage(response);
    }

    @Override
    public void updateModel(int lobbyID, Player player, int offerIndex) {
        OfferPickResponse response = new OfferPickResponse(lobbyID, player, offerIndex);
        sendMessage(response);
    }

    @Override
    public void updateModel(int lobbyID, Player player, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        OfferResolutionResponse response = new OfferResolutionResponse(lobbyID, player, topRowPicks, bottomRowPicks);
        sendMessage(response);
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players, Row topRow) {
        RoundEndResponse response = new RoundEndResponse(lobbyID, players, topRow);
        sendMessage(response);
    }

    @Override
    public void updateModel(int lobbyID, List<Player> players) {
        GameEndResponse response = new GameEndResponse(lobbyID, players);
        sendMessage(response);
    }

    @Override
    public void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
        UpdateStateResponse response = new UpdateStateResponse(lobbyID, modelStateInfo);
        sendMessage(response);
    }

    @Override
    public void createLobby(Lobby lobby, Player player) {
        CreateLobbyResponse response = new CreateLobbyResponse(lobby, player);
        sendMessage(response);
    }

    @Override
    public void startLobby(int lobbyID, Board board, List<Player> players) {
        StartLobbyResponse response = new StartLobbyResponse(lobbyID, board, players);
        sendMessage(response);
    }

    @Override
    public void stopLobby(int lobbyID) {
        StopLobbyResponse response = new StopLobbyResponse(lobbyID);
        sendMessage(response);
    }

    @Override
    public void showError(String errorMessage) {
        ErrorMessage message = new ErrorMessage(errorMessage);
        sendMessage(message);
    }

    @Override
    public void ping() {
        PingResponse message = new PingResponse();
        sendMessage(message);
    }

    @Override
    public void cleanup() {
        clientHandler.cleanup();
    }

    private void sendMessage(Response message) {
        if (isConnected)
            ServerController.getInstance().submitResponse(
                    () -> clientHandler.sendMessage(message)
            );
    }
}