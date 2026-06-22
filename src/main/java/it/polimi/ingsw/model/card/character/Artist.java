package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class Artist extends AbstractCharacter {
    public Artist(int era, boolean isFinal) {
        super(era, isFinal);
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
        p.getTribe().addArtist(this);
    }
}
