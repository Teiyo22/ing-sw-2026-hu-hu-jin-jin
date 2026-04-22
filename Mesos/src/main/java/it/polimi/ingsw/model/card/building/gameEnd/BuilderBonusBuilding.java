package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.AbstractCard;

public class BuilderBonusBuilding extends GameEndBuilding {
    public BuilderBonusBuilding(String type, int era, boolean isFinal,
                                int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public BuilderBonusBuilding(BuilderBonusBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new BuilderBonusBuilding(this);
    }

    /**
     * Adds an amount of bonus PP equal to the tribe's builder bonus PP to the player.
     * */
    @Override
    public void applyEffect() {
        owner.addPP(owner.getTribe().getBuilderBonusPP());
    }
}
