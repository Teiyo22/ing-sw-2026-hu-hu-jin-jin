package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

public abstract class AbstractBuilding extends AbstractCard implements Pickable {
    protected Player owner = null;
    @Expose protected int cost;
    @Expose protected int pp;

    public AbstractBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal);
        this.cost = cost;
        this.pp = pp;
    }

    public AbstractBuilding(AbstractBuilding source) {
        super(source);
        this.owner = source.owner;
        this.cost = source.cost;
        this.pp = source.pp;
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        owner = player;
    }

    @Override
    public void moveTo(Row row) {
        row.addBuilding(this);
    }

    @Override
    public void remove(Row row, int removedIndex){
        row.getBuildingCards().remove(removedIndex);
    }
}
