package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.NetworkServer;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Totem;

import java.util.List;
import java.util.Map;

public class ServerController extends VirtualServer {
    private static ServerController instance;

    private NetworkServer networkServer;
    private Map<String, LobbyController> waitingLobbies;
    private Map<String, LobbyController> runningLobbies;
    private Map<String, VirtualClient> clients;

    public static ServerController getInstance() {
        return instance;
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