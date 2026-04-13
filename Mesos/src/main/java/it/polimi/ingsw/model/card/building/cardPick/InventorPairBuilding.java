package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;

public class InventorPairBuilding extends CardPickBuilding {
    public InventorPairBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    public InventorPairBuilding(InventorPairBuilding source) {
        super(source);
    }
    
    @Override
    public AbstractCard clone() {
        return new InventorPairBuilding(this);
    }

    /** The method checks if the number of the picked inventor type became even.
     * If so a pair is formed thus the bonus gets added.*/
    @Override
    public void doForInventor(Inventor i) {
        if(owner.getTribe().getNumInventorType(i.getInventorType()) % 2 == 0){
            owner.addFood(3);
        }
    }

    @Override
    public void doForShaman(Shaman s) {
        return;
    }

    @Override
    public void doForHunter(Hunter h) {
        return;
    }

    @Override
    public void doForCollector(Collector c) {
        return;
    }

    @Override
    public void doForArtist(Artist a) {
        return;
    }

    @Override
    public void doForBuilder(Builder b) {
        return;
    }
}
