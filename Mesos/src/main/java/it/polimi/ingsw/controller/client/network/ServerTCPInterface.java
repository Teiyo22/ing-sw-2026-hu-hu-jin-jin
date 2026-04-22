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

    public void handleMessage(Response response) {

    }

    @Override
    public void addClient(VirtualClient client) {

    }

    @Override
    public void createLobby(int clientID, int playerNum, String playerName, Totem totem) {

    }

    @Override
    public void joinLobby(int clientID, int lobbyID, String playerName, Totem totem) {

    }

    @Override
    public void leaveLobby(int clientID, int lobbyID) {

    }

    @Override
    public void startLobby(int clientID, int lobbyID) {

    }

    @Override
    public void getWaitingLobbies(int clientID) {

    }

    @Override
    public void getLobbyInfo(int clientID, int lobbyID) {

    }

    @Override
    public void getRank(int clientID, int lobbyID) {

    }

    @Override
    public void getLeaderboard(int clientID, int playerNum) {

    }

    @Override
    public void requestPick(int clientID, int lobbyID, List<AbstractCard> topPicks, List<AbstractCard> bottomPicks) {

    }
}
