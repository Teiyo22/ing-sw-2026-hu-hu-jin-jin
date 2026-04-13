package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

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

    @Override
    public void doForInventor(Inventor i) {

    }

    @Override
    public void doForShaman(Shaman s) {

    }

    @Override
    public void doForHunter(Hunter h) {

    }

    @Override
    public void doForCollector(Collector c) {

    }

    @Override
    public void doForArtist(Artist a) {

    }

    @Override
    public void doForBuilder(Builder b) {

    }
}
