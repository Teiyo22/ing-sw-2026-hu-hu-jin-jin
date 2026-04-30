package it.polimi.ingsw.controller.common;


import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

import java.rmi.Remote;

public abstract class VirtualServer implements Remote {
    public abstract void addClient(VirtualClient client);
    public abstract void createLobby(VirtualClient client, int playerNum, Player player);
    public abstract void joinLobby(VirtualClient client, int lobbyID, Player player);
    public abstract void leaveLobby(VirtualClient client, int lobbyID);
    public abstract void startLobby(VirtualClient client, int lobbyID);
    public abstract void getWaitingLobbies(VirtualClient client);
    public abstract void getLobbyInfo(VirtualClient client, int lobbyID);
    public abstract void getRank(VirtualClient client, int lobbyID);
    public abstract void getLeaderboard(VirtualClient client, int playerNum);
    public abstract void requestPick(VirtualClient client, int lobbyID, List<Pickable> topPicks, List<Pickable> bottomPicks);
}