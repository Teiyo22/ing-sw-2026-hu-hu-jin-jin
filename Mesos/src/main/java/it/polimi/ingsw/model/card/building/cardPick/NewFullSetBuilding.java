package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class NewFullSetBuilding extends CardPickBuilding{
    transient int minForSet;
    public NewFullSetBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public NewFullSetBuilding(NewFullSetBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new NewFullSetBuilding(this);
    }


    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        minForSet = owner.getTribe().getMinChar() + 1;
    }


    @Override
    public void doForInventor(Inventor i) {
        checkAndAdd();
    }

    @Override
    public void doForShaman(Shaman s) {
        checkAndAdd();
    }

    @Override
    public void doForHunter(Hunter h) {
        checkAndAdd();
    }

    @Override
    public void doForCollector(Collector c) {
        checkAndAdd();
    }

    @Override
    public void doForArtist(Artist a) {
        checkAndAdd();
    }

    @Override
    public void doForBuilder(Builder b) {
        checkAndAdd();
    }

    /**
     * Adds bonus pp after completing a full set of characters with different types.
     * Previously completed sets are not counted. However, the non-completed set is still valid.
     * The set completion is checked by monitoring the minimum number of characters for a specific type.
     * */
    public void checkAndAdd() {
        if(owner.getTribe().getMinChar() >= minForSet){
            minForSet++;
            owner.addFood(5);
        }
    }
}
