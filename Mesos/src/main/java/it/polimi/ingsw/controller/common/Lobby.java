package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;

import java.util.Map;

public class Lobby {
    int lobbyID;
    int size;

    transient Map<Integer, Player> players = null;
    transient Board board = null;

    public Lobby(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
    }

    public void addPlayer(Integer clientID, Player player) {
        if (!players.containsKey(clientID))
            players.put(clientID, player);
    }

    public void removePlayer (Integer clientID) {
        players.remove(clientID);
    }

    public void setPlayers(Map<Integer, Player> players) {
        this.players = players;
    }

    public int getLobbyID() {
        return lobbyID;
    }
}