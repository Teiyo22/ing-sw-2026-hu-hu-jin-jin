package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.model.player.Totem;

import java.rmi.Remote;

public abstract class VirtualServer implements Remote {
    abstract void addClient(VirtualClient client);
    abstract void createLobby(int clientID, int playerNum, String playerName, Totem totem);
    abstract void joinLobby(int clientID, String lobbyID, String playerName, Totem totem);
    abstract void leaveLobby(int clientID, String lobbyID);
    abstract void startLobby(int clientID, String lobbyID);
    abstract void getWaitingLobbies(int clientID);
    abstract void getLobbyInfo(int clientID, String lobbyID);
    abstract void getRank(int clientID, String lobbyID);
}