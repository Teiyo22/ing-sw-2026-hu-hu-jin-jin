package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.model.ConfigLoader;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Board implements Serializable {
    transient private Game game;

    private Deck deck;
    private Row topRow;
    private Row bottomRow;
    private OrderSlot[] orderTile;
    private OfferTile[] offerTrack;

    public Board(Game game) {
        this.game = game;
        this.deck = new Deck(game.getPlayerConfig());
        this.topRow = new Row();
        this.bottomRow = new Row();
        offerTrack = new ConfigLoader().loadOfferTile(game.getPlayerConfig().getOfferTrackConfigFile());
        orderTile = new ConfigLoader().loadOrderSlot(game.getPlayerConfig().getOrderTileConfigFile());
    }

    public Board(Deck deck, Row topRow, Row bottomRow, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        this.game = null;
        this.deck = deck;
        this.topRow = topRow;
        this.bottomRow = bottomRow;
        this.orderTile = orderTile;
        this.offerTrack = offerTrack;
    }


    /** Creates a copy of the board with an empty deck.
     * @return a new board item that copies everything except the deck, maintaining a reference to the original object.*/
    public Board mediumCopy() {
        return new Board(
            null,
            topRow.deepCopy(),
            bottomRow.deepCopy(),
            orderTileCopy(),
            offerTrackCopy());
    }

    /** Creates an exact copy of the board.
     * @return a new board item that copies everything.
     * */
    public Board deepCopy() {
        return new Board(
            deck.deepCopy(),
            topRow.deepCopy(),
            bottomRow.deepCopy(),
            orderTileCopy(),
            offerTrackCopy());
    }

    private OrderSlot[] orderTileCopy() {
        OrderSlot[] orderTileCopy = new OrderSlot[orderTile.length];
        for (int i = 0; i < orderTile.length; i++)
            orderTileCopy[i] = orderTile[i].copy();
        return orderTileCopy;
    }

    private OfferTile[] offerTrackCopy() {
        OfferTile[] offerTrackCopy = new OfferTile[offerTrack.length];
        for (int i = 0; i < offerTrack.length; i++)
            offerTrackCopy[i] = offerTrack[i].copy();
        return offerTrackCopy;
    }

    /** Method to fix object references.
     * After deserialization the references might be different from the original,
     * this makes sure every reference points to the correct object by matching the new Player objects.*/
    public void fixReferences(List<Player> players) {
        for (Player player : players) {
            for (OrderSlot orderSlot : orderTile)
                if (player.equals(orderSlot.getAssignedPlayer()))
                    orderSlot.setPlayer(player);

            for (OfferTile offerTile : offerTrack)
                if (player.equals(offerTile.getAssignedPlayer()))
                    offerTile.setPlayer(player);
        }
    }

    public OfferTile[] getOfferTrack() {
        return offerTrack;
    }

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
