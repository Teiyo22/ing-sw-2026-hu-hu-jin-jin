package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.client.ClientController;
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
    private boolean running = false;

    transient private ClientController clientController = null;
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

    public void addPlayer(Integer clientID, Player player) {
        if (players == null)
            players = new HashMap<>();

        if (!players.containsKey(clientID))
            players.put(clientID, player);
    }

    public void initGame(Map<Integer, Tribe> tribes, Board board) {
        this.board = board;

        for(Integer clientID: tribes.keySet()) {
            players.get(clientID).setTribe(tribes.get(clientID));
        }

        running = true;
    }

    public void updateTribe(int clientID, Tribe tribe) {
        players.get(clientID).setTribe(tribe);
    }

    public void updateRows(Row topRow, Row bottomRow) {
        board.setTopRow(topRow);
        board.setBottomRow(bottomRow);
    }

    public void updateOfferTrack(OfferTile[] offerTrack) {
        board.setOfferTrack(offerTrack);
    }

    public void updateOrderTile(OrderSlot[] orderTile) {
        board.setOrderTile(orderTile);
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    public void removePlayer(int clientID) {
        players.remove(clientID);
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public int getSize() {
        return size;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void setPlayers(Map<Integer, Player> players) {
        this.players = players;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean contains(int clientID) {
        return players.containsKey(clientID);
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Lobby copy() {
        return new Lobby(lobbyID, size, players);
    }
}