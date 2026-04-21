package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.common.messages.requests.Request;
import it.polimi.ingsw.model.player.Totem;

import java.rmi.Remote;
import java.util.List;

public abstract class VirtualClient implements Remote {
    protected int id;

    abstract void setWaitingLobbies(String clientID, List<Lobby> lobbies);
    abstract void showLobbyInfo(Lobby lobby);
    abstract void setLobby(String lobbyID, String playerName, Totem totem);
    abstract void removeFromLobby(String lobbyID);
    abstract void showRank();
    abstract void showLeaderboard();
}
