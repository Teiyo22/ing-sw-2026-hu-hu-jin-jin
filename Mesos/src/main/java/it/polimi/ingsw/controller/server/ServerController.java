package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.NetworkServer;
import it.polimi.ingsw.model.player.Totem;

import java.util.Map;

public class ServerController implements VirtualServer {
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
    public void createLobby(String clientID, String lobbyName, int playerNum, String playerName, Totem totem) {
    }

    @Override
    public void joinLobby(String clientID, String lobbyID, String playerName, Totem totem) {
    }

    @Override
    public void leaveLobby(String clientID, String lobbyID) {
    }

    @Override
    public void startLobby(String lobbyID) {
    }

    @Override
    public void getWaitingLobbies(String clientID) {
    }

    @Override
    public void getLobbyInfo(String lobbyID, String clientID) {
    }

    @Override
    public void getRank(String lobbyID, String clientID) {
    }
}