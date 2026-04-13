package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class BonusPPBuilding extends GameEndBuilding {
    private int bonusPP;

    public BonusPPBuilding(String type, int era, boolean isFinal,
                           int cost, int pp, BuildingHandler buildingHandler,
                           int bonusPP) {
        super(type, era, isFinal, cost, pp, buildingHandler);
        this.bonusPP = bonusPP;
    }

    public BonusPPBuilding(BonusPPBuilding source) {
        super(source);
        this.bonusPP = source.bonusPP;
    }

    @Override
    public AbstractBuilding clone() {
        return new BonusPPBuilding(this);
    }

    @Override
    public void applyEffect() {
        owner.addPP(bonusPP);
    }
}
