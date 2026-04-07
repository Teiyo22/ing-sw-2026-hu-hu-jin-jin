package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class FullSetBuilding extends GameEndBuilding{
    public FullSetBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void applyEffect() {
        int sets = owner.getTribe().getMinChar();

        owner.addPP(6*sets);
    }
}
