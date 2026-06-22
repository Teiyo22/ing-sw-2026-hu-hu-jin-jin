package it.polimi.ingsw.controller.client.network;

import it.polimi.ingsw.controller.common.VirtualServer;
import it.polimi.ingsw.controller.server.network.ClientInterface;
import it.polimi.ingsw.controller.client.action.PlayerAction;
import it.polimi.ingsw.model.player.Totem;

public abstract class ServerInterface implements VirtualServer {
    protected boolean isConnected = false;

    public abstract void registerClient(ClientInterface client);
    public abstract void login(String clientID, String username);
    public abstract void createLobby(String clientID, int playerNum, Totem totem);
    public abstract void joinLobby(String clientID, int lobbyID, Totem totem);
    public abstract void leaveLobby(String clientID, int lobbyID);
    public abstract void startLobby(String clientID, int lobbyID);
    public abstract void getLobbyInfo(String clientID, int lobbyID);
    public abstract void getLeaderboard(String clientID, int playerNum);
    public abstract void requestAction(String clientID, int lobbyID, PlayerAction action);
    public abstract void disconnect(String clientID);
    public abstract void ping(String clientID);

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
