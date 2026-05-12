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
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.LoggerLevel;

import java.util.List;
import java.util.Map;

public class TCPClientInterface extends ClientInterface {
    private ClientHandler clientHandler;

    public TCPClientInterface(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void handleMessage(Request request) {
        request.receive(ServerController.getInstance());
    }

    @Override
    public synchronized void showWaitingLobbies(int clientID, List<Lobby> lobbies) {
        WaitingLobbyResponse response = new WaitingLobbyResponse(clientID, lobbies);
        sendMessage(response);
    }

    @Override
    public synchronized void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        setCurrLobbyController(lobbyID);
        LobbyInfoResponse response = new LobbyInfoResponse(clientID, lobbyID, players);
        sendMessage(response);
    }

    @Override
    public synchronized void addClient(int clientID, int lobbyID, Player player) {
        Logger.getInstance().print(LoggerLevel.DEBUG, "Sending AddClientResponse");
        AddClientResponse response = new AddClientResponse(clientID, lobbyID, player);
        sendMessage(response);
    }

    @Override
    public synchronized void addPlayer(int clientID, int lobbyID, Player player) {

        AddPlayerResponse response = new AddPlayerResponse(clientID, lobbyID, player);
        sendMessage(response);
    }

    @Override
    public synchronized void removeClient(int clientID, int lobbyID, Player player) {
        RemoveClientResponse response = new RemoveClientResponse(clientID, lobbyID, player);
        sendMessage(response);
    }

    @Override
    public synchronized void removePlayer(int clientID, int lobbyID, Player player) {
        RemovePlayerResponse response = new RemovePlayerResponse(clientID, lobbyID, player);
        sendMessage(response);
    }

    @Override
    public synchronized void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {
        GetLeaderboardResponse response = new GetLeaderboardResponse(clientID, leaderboard);
        sendMessage(response);

    }

    @Override
    public synchronized void updateModel(int clientID, int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        OfferPickResponse response = new OfferPickResponse(clientID, lobbyID, orderTile, offerTrack);
        sendMessage(response);
    }

    @Override
    public synchronized void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Board board) {
        OfferResolutionResponse response = new OfferResolutionResponse(clientID, lobbyID, player, tribe, board);
        sendMessage(response);
    }

    @Override
    public synchronized void updateModel(int clientID, int lobbyID, Player player, Tribe tribe, Row topRow) {
        ExtraActionResponse response = new ExtraActionResponse(clientID, lobbyID, player, tribe, topRow);
        sendMessage(response);
    }

    @Override
    public void updateModel(int clientID, int lobbyID, Map<Integer, Tribe> tribes, Row topRow, Row bottomRow) {
        RoundEndResponse response = new RoundEndResponse(clientID, lobbyID, tribes, topRow, bottomRow);
        sendMessage(response);
    }

    @Override
    public void updateModel(int clientID, int lobbyID, Map<Integer, Tribe> tribes, Map<Integer, Integer> ranking) {
        GameEndResponse response = new GameEndResponse(clientID, lobbyID, tribes, ranking);
        sendMessage(response);
    }

    @Override
    public synchronized void updateState(int clientID, int lobbyID, ModelStateInfo modelStateInfo) {
        UpdateStateResponse response = new UpdateStateResponse(clientID, lobbyID, modelStateInfo);
        sendMessage(response);
    }

    @Override
    public synchronized void createLobby(int clientID, Lobby lobby, Player player) {
        setCurrLobbyController(lobby.getLobbyID());

        CreateLobbyResponse response = new CreateLobbyResponse(clientID, lobby, player);
        sendMessage(response);
    }

    @Override
    public synchronized void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        StartLobbyResponse response = new StartLobbyResponse(clientID, lobbyID, board, tribes);
        sendMessage(response);
    }

    @Override
    public synchronized void stopLobby(int clientID, int lobbyID) {
        StopLobbyResponse response = new StopLobbyResponse(clientID, lobbyID);
        sendMessage(response);
    }

    @Override
    public synchronized void setID(int clientID) {
        this.id = clientID;

        SetIDResponse response = new SetIDResponse(clientID);
        sendMessage(response);

    }

    @Override
    public synchronized void showError(int clientID, String errorMessage) {
        ErrorMessage message = new ErrorMessage(clientID, errorMessage);
        sendMessage(message);
    }

    @Override
    public void ping() {
        PingResponse message = new PingResponse(this.id);
        sendMessage(message);
    }

    @Override
    public void cleanup() {
        clientHandler.cleanup();
    }

    private void sendMessage(Response message) {
        if (!isConnected)
            ServerController.getInstance().submitResponse(
                    () -> clientHandler.sendMessage(message)
            );
    }
}