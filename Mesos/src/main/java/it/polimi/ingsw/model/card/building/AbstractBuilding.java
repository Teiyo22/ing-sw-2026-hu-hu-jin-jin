package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.player.Player;

public abstract class AbstractBuilding extends AbstractCard implements Pickable {
    protected Player owner = null;
    protected int cost;
    protected int pp;

    protected BuildingHandler buildingHandler;

    public AbstractBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era);
        this.cost = cost;
        this.pp = pp;
        this.buildingHandler = buildingHandler;
    }

    @Override
    public void moveTo(Row row) {
        row.addBuilding(this);
    }
}
