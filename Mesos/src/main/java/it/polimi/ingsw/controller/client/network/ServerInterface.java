package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.player.Player;

import java.rmi.RemoteException;
import java.util.Set;

public abstract class ServerInterface implements VirtualServer {
    protected ClientController clientController;

    public ServerInterface(ClientController clientController) {
        this.clientController = clientController;
    }


    public void registerClient(ClientInterface client) {}
    public abstract void login(String clientID, String username);
    public abstract void createLobby(String clientID, int playerNum, Player player);
    public abstract void joinLobby(String clientID, int lobbyID, Player player);
    public abstract void leaveLobby(String clientID, int lobbyID);
    public abstract void startLobby(String clientID, int lobbyID);
    public abstract void getWaitingLobbies(String clientID);
    public abstract void getLobbyInfo(String clientID, int lobbyID);
    public abstract void getLeaderboard(String clientID, int playerNum);
    public abstract void requestCards(String clientID, int lobbyID, Set<Integer> topPicks, Set<Integer> bottomPicks);
    public abstract void requestOffer(String clientID, int lobbyID, int offerIndex);
    public abstract void disconnect();
    public abstract void ping(String clientID);

    public ClientController getClientController() {
        return clientController;
    }
}
