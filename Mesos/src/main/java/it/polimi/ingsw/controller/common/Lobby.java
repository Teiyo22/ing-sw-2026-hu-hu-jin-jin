package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Lobby implements Serializable {
    private int lobbyID;
    private int size;

    transient private Map<Player, Integer> players = null;
    transient private Board board = null;

    public Lobby(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
    }

    private Lobby(int lobbyID, int size, Map<Player, Integer> players) {
        this.lobbyID = lobbyID;
        this.size = size;
        this.players = new HashMap<>(players);
    }

    //=============================================================================
    // Player Management methods
    //=============================================================================

    public void addClient(Integer clientID, Player player) {
        if (players.containsKey(player))
            players.put(player, clientID);
    }

    public void addPlayer(Integer clientID, Player player) {
        if (!players.containsKey(player))
            players.put(player, clientID);
    }

    public void removeClient(Integer clientID, Player player) {
        if (players.containsKey(player) && players.get(player).equals(clientID))
            players.put(player, null);
    }

    public void removePlayer(Integer clientID, Player player) {
        if (players.containsKey(player) && players.get(player).equals(clientID))
            players.remove(player);
    }

    //=============================================================================
    // Model management methods
    //=============================================================================

    public void initGame(Map<Integer, Tribe> tribes, Board board) {
        this.board = board;

        for (Player player : players.keySet())
            player.setTribe(tribes.get(players.get(player)));
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public int getLobbyID() {
        return lobbyID;
    }

    public int getSize() {
        return size;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public Map<Player, Integer> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public boolean containsClient(int clientID) {
        return players.containsValue(clientID);
    }

    public Lobby copy() {
        return new Lobby(lobbyID, size, players);
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public void setPlayers(Map<Player, Integer> players) {
        this.players = players;
    }

}