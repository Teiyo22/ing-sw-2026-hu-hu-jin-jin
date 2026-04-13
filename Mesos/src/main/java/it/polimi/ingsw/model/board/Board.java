package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.utils.LoadConfig;

public class Board {
    final Game game;
    final Deck deck;
    final Row topRow;
    final Row bottomRow;
    OrderSlot[] orderTile;
    OfferTile[] offerTrack;

    public Board(Game game) {

        this.game = game;
        this.deck = new Deck(game.getGameSize(),this);
        this.deck.init();
        this.topRow = new Row();
        this.bottomRow = new Row();
    }

    /**
     * Init of OrderTile
     */
    public void initOfferTrack() {
        offerTrack = new LoadConfig().loadOfferTile(game.getGameSize().getOfferTiles());
    }

    /**
     * Init of OfferTrack
     */
    public void initOrderTile() {
        orderTile = new LoadConfig().loadOrderSlot(game.getGameSize().getOrderslots());
    }

    /**
     * Returns the offerTrack
     */
    public OfferTile[] getOfferTrack() {
        return offerTrack;
    }

    /**
     * Return the orderTile
     */
    public OrderSlot[] getOrderTile() {
        return orderTile;
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
