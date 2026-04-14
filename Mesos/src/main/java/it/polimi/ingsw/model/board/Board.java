package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.utils.ConfigLoader;

public class Board {
    final Game game;
    final Deck deck;
    final Row topRow;
    final Row bottomRow;
    OrderSlot[] orderTile;
    OfferTile[] offerTrack;

    public Board(Game game) {
        this.game = game;
        this.deck = new Deck(game.getPlayerConfig(),this);
        this.deck.init();
        this.topRow = new Row();
        this.bottomRow = new Row();
    }

    /**
     * Init of OrderTile
     */
    public void initOfferTrack() {
        offerTrack = new ConfigLoader().loadOfferTile(game.getPlayerConfig().getOfferTrackConfigFile());
    }

    /**
     * Init of OfferTrack
     */
    public void initOrderTile() {
        orderTile = new ConfigLoader().loadOrderSlot(game.getPlayerConfig().getOrderTileConfigFile());
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
