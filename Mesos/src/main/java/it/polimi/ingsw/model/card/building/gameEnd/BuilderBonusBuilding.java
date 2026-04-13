package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.Builder;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

public class BuilderBonusBuilding extends GameEndBuilding {
    public BuilderBonusBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    public BuilderBonusBuilding(BuilderBonusBuilding source) {
        super(source);
    }

    @Override
    public AbstractBuilding clone() {
        return new BuilderBonusBuilding(this);
    }

    @Override
    public void applyEffect() {
        owner.addPP(owner.getTribe().getBuilderBonusPP());
    }
}
