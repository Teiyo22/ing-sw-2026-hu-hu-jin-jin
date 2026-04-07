package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Game;

public class Board {
    final Game game;
    final Deck deck;
    final Row topRow;
    final Row bottomRow;
    final OrderSlot[] orderTile;
    final OfferTile[] offerTrack;

    public Board(Game game) {
        this.game = game;
    }

    private void initOrderTile() {

    }


    private void initOfferTrack() {

    }

    public OrderSlot[] getOrderTile(){return orderTile;}

    public Deck getDeck() {
        return deck;
    }

    public Row getTopRow() {
        return topRow;
    }

    public Row getBottomRow() {
        return bottomRow;
    }
}
