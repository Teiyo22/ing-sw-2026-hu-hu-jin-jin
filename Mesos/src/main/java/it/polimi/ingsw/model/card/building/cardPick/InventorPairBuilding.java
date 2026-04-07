package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class InventorPairBuilding extends CardPickBuilding {
    private InventorType type;

    public InventorPairBuilding(int era, int cost, int pp, BuildingHandler buildingHandler, InventorType type) {
        super(era, cost, pp, buildingHandler);
        this.type = type;
    }

    @Override
    public void doForInventor(Inventor i) {
        if(i.getInventorType() == type){
            if(owner.getTribe().getNumInventorType(type) % 2 == 0){
                owner.addFood(3);
            }
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
