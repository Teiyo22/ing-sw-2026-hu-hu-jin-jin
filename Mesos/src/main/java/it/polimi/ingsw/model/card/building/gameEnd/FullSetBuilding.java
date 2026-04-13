package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;

public class FullSetBuilding extends GameEndBuilding{
    public FullSetBuilding(String type, int era, boolean isFinal,
                           int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp);
    }

    public FullSetBuilding(FullSetBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new FullSetBuilding(this);
    }

    @Override
    public void applyEffect() {
        int sets = owner.getTribe().getMinChar();

        owner.addPP(6*sets);
    }
}
