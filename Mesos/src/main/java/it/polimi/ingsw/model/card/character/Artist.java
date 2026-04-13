package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;
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
    public void onPick(Player player) {
        player.getTribe().addArtist();
    }

    @Override
    public void accept(CardVisitor v){
        v.doForArtist(this);
    }
}
