package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class NewFullSetBuilding extends CardPickBuilding{
    int minForSet;
    public NewFullSetBuilding(String type, int era, boolean isFinal,
                              int cost, int pp, BuildingHandler buildingHandler) {
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
        owner = player;
        buildingHandler.addCardPickBuilding(this);
        minForSet = owner.getTribe().getMinChar() + 1;
    }

    /** Every picked card has the same effects.
     * If a new set is formed (the character type with the least amount meets the required minimum) the bonus is added.*/
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

    public void checkAndAdd() {
        if(owner.getTribe().getMinChar() >= minForSet){
            minForSet++;
            owner.addFood(5);
        }
    }
}
