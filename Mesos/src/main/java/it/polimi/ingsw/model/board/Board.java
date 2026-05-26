package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.utils.model.ConfigLoader;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Board implements Serializable {
    transient Game game = null;
    transient Deck deck = null;

    private Row topRow;
    private Row bottomRow;
    private OrderSlot[] orderTile;
    private OfferTile[] offerTrack;

    public Board(Game game) {
        this.game = game;
        this.deck = new Deck(game.getPlayerConfig(),this);
        this.deck.init();
        this.topRow = new Row();
        this.bottomRow = new Row();
    }

    public Board(Row topRow, Row bottomRow, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        this.topRow = topRow;
        this.bottomRow = bottomRow;
        this.orderTile = orderTile;
        this.offerTrack = offerTrack;
    }

    /**
     * Initializes the offer track by loading the configurations associated with the game's player config.
     */
    public void initOfferTrack() {
        offerTrack = new ConfigLoader().loadOfferTile(game.getPlayerConfig().getOfferTrackConfigFile());
    }

    /**
     * Initializes the order tile by loading the configurations associated with the game's player config.
     */
    public void initOrderTile() {
        orderTile = new ConfigLoader().loadOrderSlot(game.getPlayerConfig().getOrderTileConfigFile());
    }

    public OfferTile[] getOfferTrack() {
        return offerTrack;
    }

    public OrderSlot[] getOrderTile() {
        return orderTile;
    }

    public Board copy() {
        return new Board(topRow, bottomRow, orderTile, offerTrack);
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

    public void setTopRow(Row topRow) {
        this.topRow = topRow;
    }

    public void setBottomRow(Row bottomRow) {
        this.bottomRow = bottomRow;
    }

    public Game getGame() {
        return game;
    }

    public List<Pickable> getPickable(Set<Integer> picks, boolean isTop) {
        // It's fundamental that building cards are concatenated before character cards,
        // to avoid the possibility of changing the player's building discount during the pick,
        // thus influencing the cost of the buildings.
        Row row = isTop ? topRow : bottomRow;
        return Stream.concat(row.getBuildingCards().stream()
                    .filter(b -> picks.contains(b.getID()))
                    .map(b -> (Pickable) b),
                row.getCharacterCards().stream()
                    .filter(c -> picks.contains(c.getID()))
                    .map(c -> (Pickable) c))
            .toList();
    }
}
