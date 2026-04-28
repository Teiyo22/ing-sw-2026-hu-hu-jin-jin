package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.messages.requests.Request;
import it.polimi.ingsw.controller.common.messages.responses.*;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;

import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;

public class ClientTCPInterface extends VirtualClient {
    private ClientHandler clientHandler;

    public ClientTCPInterface(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void handleMessage(Request request){
        request.receive(ServerController.getInstance());
    }

    @Override
    public void setWaitingLobbies(int clientID, List<Lobby> lobbies) {
        WaitingLobbyResponse response = new WaitingLobbyResponse(clientID, lobbies);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players) {
        LobbyInfoResponse response = new LobbyInfoResponse(clientID, lobbyID, players);
        clientHandler.sendMessage(response);
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player) {
        JoinLobbyResponse response = new JoinLobbyResponse(clientID, lobbyID, player);
        clientHandler.sendMessage(response);
    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        LeaveLobbyResponse response = new LeaveLobbyResponse(clientID, lobbyID);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showRank(int clientID, int lobbyID,  Map<Integer, Integer> rankings) {
        GetRankResponse response = new GetRankResponse(clientID, lobbyID, rankings);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {
        GetLeaderboardResponse response = new GetLeaderboardResponse(clientID, leaderboard);
        clientHandler.sendMessage(response);
    }

    @Override
    public void confirmPick(int clientID, Row updatedTopRow, Row updatedBottomRow, Tribe updatedTribe)  {
        PickCardsResponse response = new PickCardsResponse(clientID, updatedTopRow, updatedBottomRow, updatedTribe);
        clientHandler.sendMessage(response);
    }

    @Override
    public void createLobby(int clientID, Lobby lobby, Player player) {
        CreateLobbyResponse response = new CreateLobbyResponse(clientID, lobby, player);
        clientHandler.sendMessage(response);
    }

    @Override
    public void startLobby(int clientID, int lobbyID, Board board, Map<Integer, Tribe> tribes) {
        StartLobbyResponse response = new StartLobbyResponse(clientID, lobbyID, board, tribes);
        clientHandler.sendMessage(response);
    }

    @Override
    public void setID(int clientID) {
        super.setID(clientID);
        SetIDResponse response = new SetIDResponse(clientID);
        clientHandler.sendMessage(response);
    }
}