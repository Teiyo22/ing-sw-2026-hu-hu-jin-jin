package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.responses.*;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

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
        if (!isConnected)
            return;

        WaitingLobbyResponse response = new WaitingLobbyResponse(clientID, lobbies);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void showLobbyInfo(int clientID, int lobbyID, Map<Player, Integer> players) {
        if (!isConnected)
            return;

        setCurrLobbyController(lobbyID);

        LobbyInfoResponse response = new LobbyInfoResponse(clientID, lobbyID, players);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void addToLobby(int clientID, int lobbyID, Player player) {
        if (!isConnected)
            return;

        JoinLobbyResponse response = new JoinLobbyResponse(clientID, lobbyID, player);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void removeClient(int clientID, int lobbyID) {
        if (!isConnected)
            return;

        RemoveClientResponse response = new RemoveClientResponse(clientID, lobbyID);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void removePlayer(int clientID, int lobbyID, Player player) {
        if (!isConnected)
            return;

        RemovePlayerResponse response = new RemovePlayerResponse(clientID, lobbyID, player);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {
        if (!isConnected)
            return;

        GetRankResponse response = new GetRankResponse(clientID, lobbyID, rankings);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {
        if (!isConnected)
            return;

        GetLeaderboardResponse response = new GetLeaderboardResponse(clientID, leaderboard);
        clientHandler.sendMessage(response);

    }

    @Override
    public synchronized void updateModel(int clientID, Board updatedBoard, Tribe updatedTribe) {
        if (!isConnected)
            return;

        UpdateModelResponse response = new UpdateModelResponse(clientID, updatedBoard, updatedTribe);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void createLobby(int clientID, Lobby lobby, Player player) {
        if (!isConnected)
            return;

        setCurrLobbyController(lobby.getLobbyID());

        CreateLobbyResponse response = new CreateLobbyResponse(clientID, lobby, player);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void startLobby(int clientID, int lobbyID, Board board, Map<Player, Tribe> tribes) {
        if (!isConnected)
            return;

        StartLobbyResponse response = new StartLobbyResponse(clientID, lobbyID, board, tribes);
        clientHandler.sendMessage(response);
    }

    @Override
    public synchronized void setID(int clientID) {
        if (!isConnected)
            return;

        this.id = clientID;

        SetIDResponse response = new SetIDResponse(clientID);
        clientHandler.sendMessage(response);

    }

    @Override
    public synchronized void showError(int clientID, String errorMessage){
        if(!isConnected)
            return;

        ErrorMessage message = new ErrorMessage(clientID, errorMessage);
        clientHandler.sendMessage(message);
    }

    @Override
    public void ping() {
        PingResponse message = new PingResponse(this.id);
        clientHandler.sendMessage(message);
    }

    @Override
    public void cleanup() {
        clientHandler.cleanup();
    }
}