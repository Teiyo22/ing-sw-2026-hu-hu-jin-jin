package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.model.player.Totem;

import java.rmi.Remote;

public abstract class VirtualServer implements Remote {
    public abstract void addClient(VirtualClient client);
    public abstract void createLobby(int clientID, int playerNum, String playerName, Totem totem);
    public abstract void joinLobby(int clientID, String lobbyID, String playerName, Totem totem);
    public abstract void leaveLobby(int clientID, String lobbyID);
    public abstract void startLobby(int clientID, String lobbyID);
    public abstract void getWaitingLobbies(int clientID);
    public abstract void getLobbyInfo(int clientID, String lobbyID);
    public abstract void getRank(int clientID, String lobbyID);
}