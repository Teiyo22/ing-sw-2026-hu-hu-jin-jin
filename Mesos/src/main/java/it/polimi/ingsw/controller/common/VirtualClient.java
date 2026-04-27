package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Tribe;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

public abstract class VirtualClient implements Remote {
    protected int id;

    public int getID() {
        return id;
    }
    public abstract void setWaitingLobbies(int clientID, Map<Integer, Lobby> lobbies);
    public abstract void showLobbyInfo(int clientID, int lobbyID, Map<Integer, Player> players);
    public abstract void setLobby(int clientID, int lobbyID, int lobbySize, Player player);
    public abstract void removeFromLobby(int clientID, int lobbyID);
    public abstract void showRank(int clientID, Map<Integer, Integer> rankings);
    public abstract void showLeaderboard(int clientID, List<LeaderboardEntry> leaderboard);
    public abstract void confirmPick(int clientID, Row topRow, Row bottomRow, Tribe tribe);
}
