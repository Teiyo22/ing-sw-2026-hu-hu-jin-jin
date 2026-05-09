package it.polimi.ingsw.model.card.character;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Shaman extends AbstractCharacter{
    @Expose private int stars;

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
        v.visit(this);
    }

    @Override
    public String toString() {
        String format = " %-10s | %-26s | %-15s | %-15s";
        String ID = String.format("ID: %d", getID());
        String ERA = String.format("Era: %d", super.getEra());
        String STARS = String.format("Stars: %d", stars);

        return String.format(format, ID, type, ERA, STARS);
    }
}
