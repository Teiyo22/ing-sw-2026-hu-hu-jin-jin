package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.NetworkServer;
import it.polimi.ingsw.model.player.Totem;

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
    public void joinLobby(int clientID, String lobbyID, String playerName, Totem totem) {

    }

    @Override
    public void leaveLobby(int clientID, String lobbyID) {

    }

    @Override
    public void startLobby(int clientID, String lobbyID) {

    }

    @Override
    public void getWaitingLobbies(int clientID) {

    }

    @Override
    public void getLobbyInfo(int clientID, String lobbyID) {

    }

    @Override
    public void getRank(int clientID, String lobbyID) {

    }
}