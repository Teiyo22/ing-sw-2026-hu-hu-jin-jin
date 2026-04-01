package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class NewFullSetBuilding extends CardPickBuilding{
    public NewFullSetBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
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
