package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualClient;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.model.player.Player;

import java.util.Set;

public abstract class ServerInterface implements VirtualServer {
    protected ClientController clientController;

    public ServerInterface(ClientController clientController) {
        this.clientController = clientController;
    }
    public abstract void addClient(VirtualClient client);
    public abstract void createLobby(int clientID, int playerNum, Player player);
    public abstract void joinLobby(int clientID, int lobbyID, Player player);
    public abstract void leaveLobby(int clientID, int lobbyID);
    public abstract void startLobby(int clientID, int lobbyID);
    public abstract void getWaitingLobbies(int clientID);
    public abstract void getLobbyInfo(int clientID, int lobbyID);
    public abstract void getRank(int clientID, int lobbyID);
    public abstract void getLeaderboard(int clientID, int playerNum);
    public abstract void requestCards(int clientID, int lobbyID, Set<Integer> topPicks, Set<Integer> bottomPicks);
    public abstract void requestOffer(int clientID, int lobbyID, int offerIndex);
    public abstract void disconnect();
    public abstract void ping(int clientID);

    public ClientController getClientController() {
        return clientController;
    }
}
