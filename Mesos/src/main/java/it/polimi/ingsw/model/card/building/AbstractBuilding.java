package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

public abstract class AbstractBuilding extends AbstractCard implements Pickable {
    transient protected Player owner = null;
    @Expose protected int cost;
    @Expose protected int pp;

    public AbstractBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal);
        this.cost = cost;
        this.pp = pp;
    }

    public AbstractBuilding(AbstractBuilding source) {
        super(source);
        this.cost = source.cost;
        this.pp = source.pp;
    }

    /**
     * Sets the card owner, removes the food cost after discount, and adds the card to the player's building handler.
     *
     * @param player is the player who picked the card.
     * @param buildingHandler is used to add the card to the building handler.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        owner = player;
        cost = Math.max(0, cost - player.getTribe().getBuilderDiscount());
        player.addFood(cost);
        player.addBuilding(this);
    }

    @Override
    public void moveTo(Row row) {
        row.addBuilding(this);
    }

    public int getPP() {
        return pp;
    }
    public int getCost() {
        return cost;
    }

    @Override
    public void removeFrom(Row row){
        row.getBuildingCards().remove(this);
    }

    public Player getOwner() {
        return owner;
    }


}
