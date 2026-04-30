package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.messages.requests.Request;
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
        try {
            WaitingLobbyResponse response = new WaitingLobbyResponse(clientID, lobbies);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        currLobbyID = lobbyID;

        try {
            LobbyInfoResponse response = new LobbyInfoResponse(clientID, lobbyID, players);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player) {
        currLobbyID = lobbyID;

        try {
            JoinLobbyResponse response = new JoinLobbyResponse(clientID, lobbyID, player);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        if (clientID == this.id) currLobbyID = -1;

        try {
            LeaveLobbyResponse response = new LeaveLobbyResponse(clientID, lobbyID);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {
        try {
            GetRankResponse response = new GetRankResponse(clientID, lobbyID, rankings);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {
        try {
            GetLeaderboardResponse response = new GetLeaderboardResponse(clientID, leaderboard);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }

    }

    @Override
    public void confirmPick(int clientID, Board updatedBoard, Tribe updatedTribe) {
        try {
            PickCardsResponse response = new PickCardsResponse(clientID, updatedBoard, updatedTribe);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player) {
        try {
            CreateLobbyResponse response = new CreateLobbyResponse(clientID, lobby, player);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        try {
            StartLobbyResponse response = new StartLobbyResponse(clientID, lobbyID, board, tribes);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void setID(int clientID) {
        this.id = clientID;

        try {
            SetIDResponse response = new SetIDResponse(clientID);
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }

    }

    @Override
    public void stopLobby(int lobbyID) {
        currLobbyID = -1;

        try {
            StopLobbyMessage message = new StopLobbyMessage(this.id, lobbyID);
            clientHandler.sendMessage(message);
        } catch (IOException e) {

        }
    }

    @Override
    public void deleteLobby(int lobbyID) {
        if (currLobbyID == lobbyID) currLobbyID = -1;

        try {
            DeleteLobbyMessage message = new DeleteLobbyMessage(this.id, lobbyID);
            clientHandler.sendMessage(message);
        } catch (IOException e) {

        }
    }

    @Override
    public void ping() throws IOException {
        PingMessage message = new PingMessage(this.id);
        clientHandler.sendMessage(message);
    }
}