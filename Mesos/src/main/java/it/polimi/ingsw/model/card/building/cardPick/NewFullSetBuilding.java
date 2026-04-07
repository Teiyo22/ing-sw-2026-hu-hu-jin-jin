package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class NewFullSetBuilding extends CardPickBuilding{
    int minForSet;

    public NewFullSetBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addCardPickBuilding(this);
        minForSet = owner.getTribe().getMinChar() + 1;
    }

    @Override
    public void doForInventor(Inventor i) {
        if(owner.getTribe().getMinChar() >= minForSet){
            minForSet++;
            owner.addFood(5);
        }
    }

    @Override
    public void doForShaman(Shaman s) {
        if(owner.getTribe().getMinChar() >= minForSet){
            minForSet++;
            owner.addFood(5);
        }
    }

    @Override
    public void doForHunter(Hunter h) {
        if(owner.getTribe().getMinChar() >= minForSet){
            minForSet++;
            owner.addFood(5);
        }
    }

    @Override
    public void doForCollector(Collector c) {
        if(owner.getTribe().getMinChar() >= minForSet){
            minForSet++;
            owner.addFood(5);
        }
    }

    @Override
    public void doForArtist(Artist a) {
        if(owner.getTribe().getMinChar() >= minForSet){
            minForSet++;
            owner.addFood(5);
        }
    }

    @Override
    public void doForBuilder(Builder b) {
        if(owner.getTribe().getMinChar() >= minForSet){
            minForSet++;
            owner.addFood(5);
        }
    }
}
