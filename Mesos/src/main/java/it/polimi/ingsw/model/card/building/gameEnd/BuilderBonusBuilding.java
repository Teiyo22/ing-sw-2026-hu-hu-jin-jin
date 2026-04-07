package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

public class BuilderBonusBuilding extends GameEndBuilding {
    public BuilderBonusBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void applyEffect() {
        owner.addPP(owner.getTribe().getBuilderBonusPP());
    }
}
