package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.model.player.Totem;

import java.rmi.Remote;

public abstract class VirtualServer implements Remote {
    abstract void addClient(VirtualClient client);
    abstract void createLobby(String clientID, String lobbyName, int playerNum, String playerName, Totem totem);
    abstract void joinLobby(String clientID, String lobbyID, String playerName, Totem totem);
    abstract void leaveLobby(String clientID, String lobbyID);
    abstract void startLobby(String lobbyID);
    abstract void getWaitingLobbies(String clientID);
    abstract void getLobbyInfo(String lobbyID, String clientID);
    abstract void getRank(String lobbyID, String clientID);
}
