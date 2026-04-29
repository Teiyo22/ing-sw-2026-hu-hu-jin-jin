package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

import java.rmi.Remote;

public abstract class VirtualServer implements Remote {
    public abstract void addClient(VirtualClient client);
    public abstract void removeClient(VirtualClient client);
    public abstract void createLobby(int clientID, int playerNum, Player player);
    public abstract void joinLobby(int clientID, int lobbyID, Player player);
    public abstract void leaveLobby(int clientID, int lobbyID);
    public abstract void startLobby(int clientID, int lobbyID);
    public abstract void getWaitingLobbies(int clientID);
    public abstract void getLobbyInfo(int clientID, int lobbyID);
    public abstract void getRank(int clientID, int lobbyID);
    public abstract void getLeaderboard(int clientID, int playerNum);
    public abstract void requestPick(int clientID, int lobbyID, List<Pickable> topPicks, List<Pickable> bottomPicks);
}