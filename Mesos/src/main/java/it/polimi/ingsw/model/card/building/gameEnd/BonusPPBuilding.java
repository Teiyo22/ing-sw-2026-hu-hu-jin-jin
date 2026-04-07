package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class BonusPPBuilding extends GameEndBuilding {
    private int bonusPP;

    public BonusPPBuilding(int era, int cost, int pp, BuildingHandler buildingHandler, int bonusPP) {
        super(era, cost, pp, buildingHandler);
        this.bonusPP = bonusPP;
    }

    @Override
    public void applyEffect() {
        owner.addPP(bonusPP);
    }
}
