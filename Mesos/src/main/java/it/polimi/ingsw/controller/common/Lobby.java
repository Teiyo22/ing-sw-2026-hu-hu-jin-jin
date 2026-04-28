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
    transient private Map<Integer, Player> players;
    transient private Board board;

    void initGame(Map<Integer, Tribe> tribes, Board board) {
        this.board = board;

        for(Integer clientID: tribes.keySet()) {
            players.get(clientID).setTribe(tribes.get(clientID));
        }
    }

    void updateTribe(int clientID, Tribe tribe) {
        players.get(clientID).setTribe(tribe);
    }

    void updateRows(Row topRow, Row bottomRow) {
        board.setTopRow(topRow);
        board.setBottomRow(bottomRow);
    }

    void updateOfferTrack(OfferTile[] offerTrack) {
        board.setOfferTrack(offerTrack);
    }

    void updateOrderTile(OrderSlot[] orderTile) {
        board.setOrderTile(orderTile);
    }

    void updateBoard(Board board) {
        updateRows(board.getTopRow(), board.getBottomRow());
        updateOfferTrack(board.getOfferTrack());
        updateOrderTile(board.getOrderTile());
    }

    void removePlayer(int clientID) {
        players.remove(clientID);
    }

    void addPlayer(int clientID, Player player) {
        players.put(clientID, player);
    }

    public int getLobbyID() {
        return lobbyID;
    }


    public int getSize() {
        return size;
    }

}
