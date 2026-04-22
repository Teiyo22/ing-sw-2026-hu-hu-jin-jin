package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;

public class FullSetBuilding extends GameEndBuilding{
    public FullSetBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public FullSetBuilding(FullSetBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new FullSetBuilding(this);
    }

    /**
     * Adds bonus PP for each full set of characters with different types owned by the player.
     * */
    @Override
    public void applyEffect() {
        int setNum = owner.getTribe().getMinChar();
        owner.addPP(6 * setNum);
    }
}
