package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.common.messages.requests.*;
import it.polimi.ingsw.controller.common.messages.responses.Response;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Totem;

import java.util.List;

public class ServerTCPInterface extends VirtualServer {
    private ClientController clientController;
    private NetworkClient serverHandler;

    public ServerTCPInterface(ClientController clientController,  NetworkClient serverHandler) {
        this.clientController = clientController;
        this.serverHandler = serverHandler;
        serverHandler.setServer(this);
    }

    public void handleMessage(Response response) {
        response.receive(clientController);
    }

    public void addClient(VirtualClient client) {}

    @Override
    public void createLobby(int clientID, int playerNum, String playerName, Totem totem) {
        CreateLobbyRequest request = new CreateLobbyRequest(clientID, playerNum, playerName, totem);
        serverHandler.sendMessage(request);
    }

    @Override
    public void joinLobby(int clientID, int lobbyID, String playerName, Totem totem) {
        JoinLobbyRequest request = new JoinLobbyRequest(clientID, lobbyID, playerName, totem);
        serverHandler.sendMessage(request);
    }

    @Override
    public void leaveLobby(int clientID, int lobbyID) {
        LeaveLobbyRequest request = new LeaveLobbyRequest(clientID, lobbyID);
        serverHandler.sendMessage(request);
    }

    @Override
    public void startLobby(int clientID, int lobbyID) {
        StartLobbyRequest request = new StartLobbyRequest(clientID, lobbyID);
        serverHandler.sendMessage(request);
    }

    @Override
    public void getWaitingLobbies(int clientID) {
        WaitingLobbyRequest request = new WaitingLobbyRequest(clientID);
        serverHandler.sendMessage(request);
    }

    @Override
    public void getLobbyInfo(int clientID, int lobbyID) {
        LobbyInfoRequest request = new LobbyInfoRequest(clientID, lobbyID);
        serverHandler.sendMessage(request);
    }

    @Override
    public void getRank(int clientID, int lobbyID) {
        GetRankRequest request = new GetRankRequest(clientID, lobbyID);
        serverHandler.sendMessage(request);
    }

    @Override
    public void getLeaderboard(int clientID, int playerNum) {
        GetLeaderboardRequest request = new GetLeaderboardRequest(clientID, playerNum);
        serverHandler.sendMessage(request);
    }

    @Override
    public void requestPick(int clientID, int lobbyID, List<AbstractCard> topPicks, List<AbstractCard> bottomPicks) {
        PickCardsRequest request = new PickCardsRequest(clientID, lobbyID, topPicks, bottomPicks);
        serverHandler.sendMessage(request);
    }
}
