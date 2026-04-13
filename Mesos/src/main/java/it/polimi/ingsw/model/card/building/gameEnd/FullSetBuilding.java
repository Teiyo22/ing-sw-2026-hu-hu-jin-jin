package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class FullSetBuilding extends GameEndBuilding{
    public FullSetBuilding(String type, int era, boolean isFinal,
                           int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    @Override
    public void applyEffect() {

    }
}
