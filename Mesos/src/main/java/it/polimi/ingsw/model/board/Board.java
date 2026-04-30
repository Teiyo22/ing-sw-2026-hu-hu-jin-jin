package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.character.AbstractCharacter;
import it.polimi.ingsw.utils.model.ConfigLoader;

import java.util.Arrays;

public class Board {
    transient Game game = null;
    transient Deck deck = null;
    Row topRow;
    Row bottomRow;
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

    public void setOfferTrack(OfferTile[] offerTrack) {
        this.offerTrack = offerTrack;
    }

    public void setOrderTile(OrderSlot[] orderTile) {
        this.orderTile = orderTile;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Order tile: " + String.join(", ",
                Arrays.stream(orderTile).map(o -> o.getAssignedPlayer().getName()).toList()) + "\n");

        res.append("Turn: ").append(game.getGameState().getCurrPlayer().getName()).append("\n");

        res.append("TOP ROW\n");
        if (!topRow.getCharacterCards().isEmpty()) {
            for(AbstractCharacter c : topRow.getCharacterCards()){
                res.append("    ").append(c.toString()).append("\n");
            }
        }
        if(!topRow.getBuildingCards().isEmpty()) {
            for(AbstractBuilding c : topRow.getBuildingCards()){
                res.append("    ").append(c.toString()).append("\n");
            }
        }
        res.append("BOTTOM ROW\n");
        if (!bottomRow.getCharacterCards().isEmpty()) {
            for(AbstractCharacter c : bottomRow.getCharacterCards()){
                res.append("    ").append(c.toString()).append("\n");
            }
        }
        if(!bottomRow.getBuildingCards().isEmpty()) {
            for(AbstractBuilding c : bottomRow.getBuildingCards()){
                res.append("    ").append(c.toString()).append("\n");
            }
        }
        return res.toString();
    }
}
