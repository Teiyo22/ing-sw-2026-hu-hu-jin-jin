package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionBuilding extends AbstractBuilding{
    public ExtraActionBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public ExtraActionBuilding(ExtraActionBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new ExtraActionBuilding(this);
    }

    /**
     * Adds the card to the list of extra actions buildings in the building handler.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addExtraActionBuilding(this);
    }

    /**
     * The effect is applied when all actions are resolved and before the turn end.
     * The owner is allowed to pick an extra card from the top row.
     * */
    public void applyEffect(ExtraActionState state){
        state.setCurrPlayer(owner);
    }
}
