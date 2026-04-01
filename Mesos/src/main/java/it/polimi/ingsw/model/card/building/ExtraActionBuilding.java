package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionBuilding extends AbstractBuilding{
    public ExtraActionBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void onPick(Player player) {

    }

    public void applyEffect(ExtraActionState state){

    }
}
