package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public class NewFullSetBuilding extends CardPickBuilding{
    transient int minForSet;
    public NewFullSetBuilding(String type, int era, boolean isFinal,
                              int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    public NewFullSetBuilding(NewFullSetBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new NewFullSetBuilding(this);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addCardPickBuilding(this);
        minForSet = owner.getTribe().getMinChar() + 1;
    }

    /** Every picked card has the same effects.
     * If a new set is formed (the character type with the least amount meets the required minimum) the bonus is added.*/
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
