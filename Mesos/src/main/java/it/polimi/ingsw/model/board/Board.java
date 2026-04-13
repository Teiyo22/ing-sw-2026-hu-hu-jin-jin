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
        this.deck= new Deck(game.getGameSize(), this);
        this.topRow = new Row();
        this.bottomRow = new Row();
        this.orderTile = new OrderSlot[game.getGameSize()];
        this.offerTrack = new OfferTile[game.getGameSize()];


    }
    /**
    * Inizializzazione di ogni singola cella dell'orderTile*/
    public void initOrderTile() {
    }

    /**
     * Inizializzazione di ogni singola cella dell'offerTile*/
    public void initOfferTrack() {
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
