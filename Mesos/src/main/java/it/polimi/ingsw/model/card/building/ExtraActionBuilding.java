package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionBuilding extends AbstractBuilding{
    public ExtraActionBuilding(String type, int era, boolean isFinal,
                               int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    public ExtraActionBuilding(ExtraActionBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new ExtraActionBuilding(this);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addExtraActionBuilding();
    }

    public void applyEffect(ExtraActionState state){
        state.setCurrPlayer(owner);
    }
}
