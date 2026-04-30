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

public class ClientTCPInterface extends ClientInterface {
    private ClientHandler clientHandler;

    public ClientTCPInterface(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void handleMessage(Request request) {
        request.receive(ServerController.getInstance());
    }

    @Override
    public void setWaitingLobbies(int clientID, List<Lobby> lobbies) {
        WaitingLobbyResponse response = new WaitingLobbyResponse(clientID, lobbies);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        LobbyInfoResponse response = new LobbyInfoResponse(clientID, lobbyID, players);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player) {
        JoinLobbyResponse response = new JoinLobbyResponse(clientID, lobbyID, player);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        LeaveLobbyResponse response = new LeaveLobbyResponse(clientID, lobbyID);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {
        GetRankResponse response = new GetRankResponse(clientID, lobbyID, rankings);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {
        GetLeaderboardResponse response = new GetLeaderboardResponse(clientID, leaderboard);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }

    }

    @Override
    public void confirmPick(int clientID, Board updatedBoard, Tribe updatedTribe) {
        PickCardsResponse response = new PickCardsResponse(clientID, updatedBoard, updatedTribe);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player) {
        CreateLobbyResponse response = new CreateLobbyResponse(clientID, lobby, player);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        StartLobbyResponse response = new StartLobbyResponse(clientID, lobbyID, board, tribes);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }
    }

    @Override
    public void setID(int clientID) {
        this.id = clientID;

        SetIDResponse response = new SetIDResponse(clientID);

        try {
            clientHandler.sendMessage(response);
        } catch (IOException e) {

        }

    }

    @Override
    public void stopLobby(int lobbyID) {

        try {

        } catch (IOException e) {

        }
    }

    @Override
    public void deleteLobby(int lobbyID) {
        try {

        } catch (IOException e) {

        }
    }

    @Override
    public void ping() throws IOException {
        PingMessage message = new PingMessage(this.id);
        clientHandler.sendMessage(message);
    }
}