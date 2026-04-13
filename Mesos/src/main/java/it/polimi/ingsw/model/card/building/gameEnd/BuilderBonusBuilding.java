package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class BuilderBonusBuilding extends GameEndBuilding {
    public BuilderBonusBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    @Override
    public void applyEffect() {

    }
}
