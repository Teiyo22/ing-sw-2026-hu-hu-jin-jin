package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.common.messages.requests.Request;
import it.polimi.ingsw.model.player.Totem;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

public abstract class VirtualClient implements Remote {
    protected int id;

    abstract void setWaitingLobbies(int clientID, List<Lobby> lobbies);
    abstract void showLobbyInfo(int clientID, Lobby lobby);
    abstract void setLobby(int clientID, int lobbyID, String playerName, Totem totem);
    abstract void removeFromLobby(int clientID, int lobbyID);
    abstract void showRank(int clientID, Map<Integer, Integer> rankings);
    abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard);
}
