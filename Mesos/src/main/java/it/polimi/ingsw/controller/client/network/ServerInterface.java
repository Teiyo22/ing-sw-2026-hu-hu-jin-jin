package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.model.action.PlayerAction;
import it.polimi.ingsw.model.player.Totem;

import java.util.Set;

/** Abstract class that defines what a virtual server should implement.
 * It is used by the client to call server's methods. The actual implementations depend on the chosen communication protocol.
 * */
public abstract class ServerInterface implements VirtualServer {
    protected boolean isConnected = false;
    protected ClientController clientController;

    public ServerInterface(ClientController clientController) {
        this.clientController = clientController;
    }


    public void registerClient(ClientInterface client) {}
    public abstract void login(String clientID, String username);
    public abstract void createLobby(String clientID, int playerNum, Totem totem);
    public abstract void joinLobby(String clientID, int lobbyID, Totem totem);
    public abstract void leaveLobby(String clientID, int lobbyID);
    public abstract void startLobby(String clientID, int lobbyID);
    public abstract void getLobbyInfo(String clientID, int lobbyID);
    public abstract void getLeaderboard(String clientID, int playerNum);
    public abstract void requestAction(String clientID, int lobbyID, PlayerAction action);
    public abstract void disconnect();
    public abstract void ping(String clientID);

    public ClientController getClientController() {
        return clientController;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
