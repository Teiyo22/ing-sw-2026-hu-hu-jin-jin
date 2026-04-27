package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.card.AbstractCard;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

public abstract class VirtualClient implements Remote {
    protected int id;

    public void setID(int clientID){
        this.id = clientID;
    }
    public int getID(){
        return id;
    }
    public abstract void setWaitingLobbies(int clientID, Map<Integer, Lobby> lobbies);
    public abstract void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players);
    public abstract void setLobby(int clientID, int lobbyID, Player player);
    public abstract void removeFromLobby(int clientID, int lobbyID);
    public abstract void showRank(int clientID, Map<Integer, Integer> rankings);
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard);
    public abstract void confirmPick(int clientID, List<AbstractCard> topPicks, List<AbstractCard> bottomPicks);
}
