package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class CavePaintingBuilding extends AbstractBuilding{
    private int bonusFood;

    public CavePaintingBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, BuildingHandler buildingHandler,
                                int bonusFood) {
        super(type, era, isFinal, cost, pp, buildingHandler);
        this.bonusFood = bonusFood;
    }

    @Override
    public void onPick(Player player) {

    }

    public void applyEffect() {

    }
}
