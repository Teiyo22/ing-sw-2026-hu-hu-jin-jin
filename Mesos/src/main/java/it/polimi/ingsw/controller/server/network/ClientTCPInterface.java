package it.polimi.ingsw.controller.server.network;

import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.messages.requests.Request;
import it.polimi.ingsw.controller.common.messages.responses.*;
import it.polimi.ingsw.controller.server.ServerController;
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
    private ServerController serverController;

    public ClientTCPInterface(ClientHandler clientHandler, ServerController serverController) {
        this.clientHandler = clientHandler;
        this.serverController = serverController;
    }

    public void handleMessage(Request request){
        request.receive(serverController);
    }

    @Override
    public void setWaitingLobbies(int clientID, List<Lobby> lobbies) {
        WaitingLobbyResponse response = new WaitingLobbyResponse(clientID, lobbies);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showLobbyInfo(int clientID, Lobby lobby) {
        LobbyInfoResponse response = new LobbyInfoResponse(clientID, lobby );
        clientHandler.sendMessage(response);
    }

    @Override
    public void setLobby(int clientID, int lobbyID, Player player) {

    }

    @Override
    public void removeFromLobby(int clientID, int lobbyID) {
        LeaveLobbyResponse response = new LeaveLobbyResponse(clientID, lobbyID);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showRank(int clientID, int lobbyID, Map<Integer, Integer> rankings) {
        GetRankResponse response = new GetRankResponse(clientID, lobbyID, rankings);
        clientHandler.sendMessage(response);
    }

    @Override
    public void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard) {
        GetLeaderboardResponse response = new GetLeaderboardResponse(clientID, leaderboard);
        clientHandler.sendMessage(response);
    }

    @Override
    public void confirmPick(int clientID, int lobbyID, Row topRow, Row bottomRow, Tribe tribe) {
        PickCardsResponse response = new PickCardsResponse(clientID,lobbyID, topRow, bottomRow, tribe);
        clientHandler.sendMessage(response);
    }
}