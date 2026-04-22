package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.character.*;

public class InventorPairBuilding extends CardPickBuilding {
    public InventorPairBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public InventorPairBuilding(InventorPairBuilding source) {
        super(source);
    }
    
    @Override
    public AbstractCard clone() {
        return new InventorPairBuilding(this);
    }


    /**
     * After the completion of a pair of inventors of the same type, adds food to the owner.
     * */
    @Override
    public void doForInventor(Inventor i) {
        if (owner.getTribe().getNumInventorType(i.getInventorType()) % 2 == 0) {
            owner.addFood(3);
        }
    }
}
