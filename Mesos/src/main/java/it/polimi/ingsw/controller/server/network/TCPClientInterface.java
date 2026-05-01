package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.common.messages.responses.*;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.IOException;
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
    public void showWaitingLobbies(int clientID, List<Lobby> lobbies) {
        if (!isConnected)
            return;

        WaitingLobbyResponse response = new WaitingLobbyResponse(clientID, lobbies);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        if (!isConnected)
            return;

        currLobbyID = lobbyID;

        LobbyInfoResponse response = new LobbyInfoResponse(clientID, lobbyID, players);
        clientHandler.sendMessage(response);
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player) {
        if (!isConnected)
            return;

        if (clientID == this.id) currLobbyID = lobbyID;

        JoinLobbyResponse response = new JoinLobbyResponse(clientID, lobbyID, player);
        clientHandler.sendMessage(response);
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        if (!isConnected)
            return;

        if (clientID == this.id) currLobbyID = -1;

        LeaveLobbyResponse response = new LeaveLobbyResponse(clientID, lobbyID);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {
        if (!isConnected)
            return;

        GetRankResponse response = new GetRankResponse(clientID, lobbyID, rankings);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {
        if (!isConnected)
            return;

        GetLeaderboardResponse response = new GetLeaderboardResponse(clientID, leaderboard);
        clientHandler.sendMessage(response);

    }

    @Override
    public void confirmPick(int clientID, Board updatedBoard, Tribe updatedTribe) {
        if (!isConnected)
            return;

        PickCardsResponse response = new PickCardsResponse(clientID, updatedBoard, updatedTribe);
        clientHandler.sendMessage(response);
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player) {
        if (!isConnected)
            return;

        currLobbyID = lobby.getLobbyID();

        CreateLobbyResponse response = new CreateLobbyResponse(clientID, lobby, player);
        clientHandler.sendMessage(response);
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        if (!isConnected)
            return;

        StartLobbyResponse response = new StartLobbyResponse(clientID, lobbyID, board, tribes);
        clientHandler.sendMessage(response);
    }

    @Override
    public void setID(int clientID) {
        if (!isConnected)
            return;

        this.id = clientID;

        SetIDResponse response = new SetIDResponse(clientID);
        clientHandler.sendMessage(response);

    }

    @Override
    public void stopLobby(int lobbyID) {
        if (!isConnected)
            return;

        currLobbyID = -1;

        StopLobbyMessage message = new StopLobbyMessage(this.id, lobbyID);
        clientHandler.sendMessage(message);
    }

    @Override
    public void deleteLobby(int lobbyID) {
        if (currLobbyID == lobbyID) currLobbyID = -1;

        DeleteLobbyMessage message = new DeleteLobbyMessage(this.id, lobbyID);
        clientHandler.sendMessage(message);
    }

    @Override
    public void ping() throws IOException {
        PingMessage message = new PingMessage(this.id);
        clientHandler.sendMessage(message);
    }
}