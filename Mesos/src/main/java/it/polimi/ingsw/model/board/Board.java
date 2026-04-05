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
        this.deck= new Deck();
        this.topRow = new Row(game);
        this.bottomRow = new Row(game);
        this.orderTile = new OrderSlot[gameSize];
        this.offerTrack = new OfferTile[gameSize];


    }
    /**
    * Inizializzazione di ogni singola cella dell'orderTile*/
    private void initOrderTile() {
        for (int i = 0; i < orderTile.length; i++) {
            orderTile[i]= new OrderSlot(i);
        }
    }

    /**
     * Inizializzazione di ogni singola cella dell'offerTile*/
    private void initOfferTrack() {
        for (int i = 0; i < offerTrack.length; i++) {
            offerTrack[i]= new OfferTile(i);
        }
    }

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
