package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.model.player.Totem;

import java.rmi.Remote;

public interface VirtualServer extends Remote {
    void addClient(VirtualClient client);
    void createLobby(String clientID, String lobbyName, int playerNum, String playerName, Totem totem);
    void joinLobby(String clientID, String lobbyID, String playerName, Totem totem);
    void leaveLobby(String clientID, String lobbyID);
    void startLobby(String lobbyID);
    void getWaitingLobbies(String clientID);
    void getLobbyInfo(String lobbyID, String clientID);
    void getRank(String lobbyID, String clientID);
}
