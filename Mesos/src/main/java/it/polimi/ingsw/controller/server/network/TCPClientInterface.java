package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.controller.common.messages.responses.*;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    public synchronized void setID(String clientID) {
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
    public synchronized void showWaitingLobbies(List<Lobby> lobbies) {
        WaitingLobbyResponse response = new WaitingLobbyResponse(lobbies);
        sendMessage(response);
    }

    @Override
    public synchronized void showLobbyInfo(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers) {
        setCurrLobbyController(lobbyID);
        LobbyInfoResponse response = new LobbyInfoResponse(lobbyID, connectedPlayers, disconnectedPlayers);
        sendMessage(response);
    }

    @Override
    public synchronized void addPlayer(int lobbyID, Player player) {
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
    public synchronized void removeClient(int lobbyID, Player player) {
        RemoveClientResponse response = new RemoveClientResponse(lobbyID, player);
        sendMessage(response);
    }

    @Override
    public synchronized void removePlayer(int lobbyID, Player player) {
        RemovePlayerResponse response = new RemovePlayerResponse(lobbyID, player);
        sendMessage(response);
    }

    @Override
    public synchronized void showLeaderboard(List<LeaderboardEntry> leaderboard) {
        GetLeaderboardResponse response = new GetLeaderboardResponse(leaderboard);
        sendMessage(response);
    }

    @Override
    public synchronized void updateModel(int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        OfferPickResponse response = new OfferPickResponse(lobbyID, orderTile, offerTrack);
        sendMessage(response);
    }

    @Override
    public synchronized void updateModel(int lobbyID, Player player, Board board) {
        OfferResolutionResponse response = new OfferResolutionResponse(lobbyID, player, board);
        sendMessage(response);
    }

    @Override
    public synchronized void updateModel(int lobbyID, Player player, Row topRow) {
        ExtraActionResponse response = new ExtraActionResponse(lobbyID, player, topRow);
        sendMessage(response);
    }

    @Override
    public void updateModel(int lobbyID, Collection<Player> players, Row topRow, Row bottomRow) {
        RoundEndResponse response = new RoundEndResponse(lobbyID, players, topRow, bottomRow);
        sendMessage(response);
    }

    @Override
    public void updateModel(int lobbyID, Collection<Player> players) {
        GameEndResponse response = new GameEndResponse(lobbyID, players);
        sendMessage(response);
    }

    @Override
    public synchronized void updateState(int lobbyID, ModelStateInfo modelStateInfo) {
        UpdateStateResponse response = new UpdateStateResponse(lobbyID, modelStateInfo);
        sendMessage(response);
    }

    @Override
    public synchronized void createLobby(Lobby lobby, Player player) {
        setCurrLobbyController(lobby.getLobbyID());

        CreateLobbyResponse response = new CreateLobbyResponse(lobby, player);
        sendMessage(response);
    }

    @Override
    public synchronized void startLobby(int lobbyID, Board board, Map<String, Tribe> tribes) {
        StartLobbyResponse response = new StartLobbyResponse(lobbyID, board, tribes);
        sendMessage(response);
    }

    @Override
    public synchronized void stopLobby(int lobbyID) {
        StopLobbyResponse response = new StopLobbyResponse(lobbyID);
        sendMessage(response);
    }

    @Override
    public synchronized void showError(String errorMessage) {
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