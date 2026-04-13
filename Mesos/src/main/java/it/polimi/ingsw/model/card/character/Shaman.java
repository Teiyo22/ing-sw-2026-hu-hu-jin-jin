package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class Shaman extends AbstractCharacter{
    private int stars;

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
    public void onPick(Player player) {

    }
}
