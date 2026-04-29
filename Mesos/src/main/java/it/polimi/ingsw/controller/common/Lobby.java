package it.polimi.ingsw.controller.common;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Map;

public class Lobby {
    private int lobbyID;
    private int size;

    transient private ClientController clientController;
    transient private Map<Integer, Player> players = null;
    transient private Board board = null;

    public Lobby(int lobbyID, int size) {
        this.lobbyID = lobbyID;
        this.size = size;
    }

    public void addPlayer(Integer clientID, Player player) {
        if (!players.containsKey(clientID))
            players.put(clientID, player);
    }

    public void initGame(Map<Integer, Tribe> tribes, Board board) {
        this.board = board;

        for(Integer clientID: tribes.keySet()) {
            players.get(clientID).setTribe(tribes.get(clientID));
        }
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
        updateRows(board.getTopRow(), board.getBottomRow());
        updateOfferTrack(board.getOfferTrack());
        updateOrderTile(board.getOrderTile());
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

    public void setPlayers(Map<Integer, Player> players) {
        this.players = players;
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }
}