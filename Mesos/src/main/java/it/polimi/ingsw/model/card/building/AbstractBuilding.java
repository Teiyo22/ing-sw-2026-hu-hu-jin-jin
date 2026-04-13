package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

public abstract class AbstractBuilding extends AbstractCard implements Pickable {
    protected Player owner = null;
    protected int cost;
    protected int pp;
    protected transient BuildingHandler buildingHandler = null;

    public AbstractBuilding(String type, int era, boolean isFinal, int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal);
        this.cost = cost;
        this.pp = pp;
        this.buildingHandler = buildingHandler;
    }

    public AbstractBuilding(AbstractBuilding source) {
        super(source);
        this.owner = source.owner;
        this.cost = source.cost;
        this.pp = source.pp;
    }

    @Override
    public void onPick(Player player) {
        owner = player;
    }

    public abstract AbstractBuilding clone();

    @Override
    public void moveTo(Row row) {
        row.addBuilding(this);
    }

    @Override
    public void remove(Row row, int removedIndex){
        row.getBuildingCards().remove(removedIndex);
    }

    public void setBuildingHandler(BuildingHandler buildingHandler) {
        this.buildingHandler = buildingHandler;
    }
}
