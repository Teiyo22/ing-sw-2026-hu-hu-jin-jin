package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;

public class BuilderBonusBuilding extends GameEndBuilding {
    public BuilderBonusBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp);
    }

    public BuilderBonusBuilding(BuilderBonusBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new BuilderBonusBuilding(this);
    }

    @Override
    public void applyEffect() {
        owner.addPP(owner.getTribe().getBuilderBonusPP());
    }
}
