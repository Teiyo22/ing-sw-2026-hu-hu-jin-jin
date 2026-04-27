package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.card.AbstractCard;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

public abstract class VirtualClient implements Remote {
    protected int id;

    public abstract void setWaitingLobbies(int clientID, List<Lobby> lobbies);
    public abstract void showLobbyInfo(int clientID, Lobby lobby);
    public abstract void setLobby(int clientID, int lobbyID, String playerName, Totem totem);
    public abstract void removeFromLobby(int clientID, int lobbyID);
    public abstract void showRank(int clientID, Map<Integer, Integer> rankings);
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard);
    public abstract void confirmPick(int clientID, List<AbstractCard> topPicks, List<AbstractCard> bottomPicks);

    public Integer getID() {
    }
}
