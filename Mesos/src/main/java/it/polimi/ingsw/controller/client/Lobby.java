package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.controller.client.turn.*;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Lobby implements Serializable {
    private int lobbyID;
    private int size;

    transient private Player shownPlayer = null;
    transient private Map<Player, Integer> players = null;
    transient private Board board = null;
    transient private TurnState turnState = null;

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

    public void updateOrderTile(OrderSlot[] orderTile) {
        this.board.setOrderTile(orderTile);
    }

    public void updateOfferTrack(OfferTile[] offerTrack) {
        this.board.setOfferTrack(offerTrack);
    }

    public void updateTribe(Player player, Tribe tribe) {
        for (Player p : players.keySet())
            if (p.equals(player))
                p.setTribe(tribe);
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    public void updateTopRow(Row row) {
        this.board.setTopRow(row);
    }

    public void updateBottomRow(Row row) {
        this.board.setBottomRow(row);
    }

    public void updateTribes(Map<Integer, Tribe> tribes) {
        for (Map.Entry<Player, Integer> entry : players.entrySet()) {
            Tribe tribe = tribes.get(entry.getValue());
            if (tribe != null) {
                entry.getKey().setTribe(tribe);
            }
        }
    }

    public void setRanking(Map<Integer, Integer> ranking) {
        for (Map.Entry<Player, Integer> entry : players.entrySet()) {
            entry.getKey().setRank(ranking.get(entry.getValue()));
        }
    }

    public void showPlayer(Player player) {
        shownPlayer = player;
    }

    public void hidePlayer() {
        shownPlayer = null;
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

    public Player getShownPlayer() {
        return shownPlayer;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public Player getPlayer(int clientID) {
        for (Map.Entry<Player, Integer> entry : players.entrySet())
            if (entry.getValue().equals(clientID))
                return entry.getKey();
        return null;
    }

    public Player getCurrPlayer() {
        if (turnState == null)
            return null;

        for (Map.Entry<Player, Integer> entry : players.entrySet())
            if (entry.getKey().equals(turnState.getCurrPlayer()))
                return entry.getKey();
        return null;
    }

    public boolean containsClient(int clientID) {
        return players.containsValue(clientID);
    }

    public Lobby copy() {
        return new Lobby(lobbyID, size, players);
    }

    public boolean isShownPlayer() {
        return shownPlayer != null;
    }

    //=============================================================================
    // Getters
    //=============================================================================

    public void setPlayers(Map<Player, Integer> players) {
        this.players = players;
    }


    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public void setIdleTurnState() {
        this.turnState = new IdleState(turnState.getCurrPlayer(), turnState.getIndex());
    }

}