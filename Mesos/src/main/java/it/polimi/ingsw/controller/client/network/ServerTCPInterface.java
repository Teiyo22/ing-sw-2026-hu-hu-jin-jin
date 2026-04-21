package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.model.player.Totem;

public class ServerTCPInterface extends VirtualServer {
    private ClientController clientController;
    private NetworkClient serverHandler;


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
