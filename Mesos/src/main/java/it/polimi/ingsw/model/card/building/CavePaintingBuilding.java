package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class CavePaintingBuilding extends AbstractBuilding{
    private int bonusFood;

    public CavePaintingBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, BuildingHandler buildingHandler,
                                int bonusFood) {
        super(type, era, isFinal, cost, pp, buildingHandler);
        this.bonusFood = bonusFood;
    }

    public CavePaintingBuilding(CavePaintingBuilding source) {
        super(source);
        this.bonusFood = source.bonusFood;
    }

    @Override
    public AbstractCard clone() {
        return new CavePaintingBuilding(this);
    }

    @Override
    public void onPick(Player player) {

    }

    public void applyEffect() {

    }
}
