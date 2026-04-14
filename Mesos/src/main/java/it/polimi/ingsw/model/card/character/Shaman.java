package it.polimi.ingsw.model.card.character;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Shaman extends AbstractCharacter{
    @Expose private final int stars;

    public Shaman(String type, int era, boolean isFinal, int stars) {
        super(type, era, isFinal);
        this.stars = stars;
    }

    public Shaman(Shaman source) {
        super(source);
        this.stars = source.stars;
    }

    @Override
    public AbstractCard clone() {
        return new Shaman(this);
    }

    @Override
    public void addToTribeOf(Player p) {
        p.getTribe().addShaman(this);
        p.getTribe().addStars(stars);
    }

    public int getStar(){
        return this.stars;
    }

    @Override
    public void accept(CardVisitor v){
        v.doForShaman(this);
    }
}
