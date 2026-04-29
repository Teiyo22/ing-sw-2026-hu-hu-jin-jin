package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.CardVisitor;
import it.polimi.ingsw.model.player.Player;

public class Artist extends AbstractCharacter {
    public Artist(String type, int era, boolean isFinal) {
        super(type, era, isFinal);
    }

    public Artist(Artist source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new Artist(this);
    }

    @Override
    public void addToTribeOf(Player p) {
        p.getTribe().addArtist();
    }

    @Override
    public void accept(CardVisitor v){
        v.visit(this);
    }

    @Override
    public String toString() {
        return String.format("[ Type: %s  |  Era: %d ]", super.getType(), super.getEra());
    }
}
