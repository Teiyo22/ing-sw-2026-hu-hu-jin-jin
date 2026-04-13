package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class Hunter extends AbstractCharacter {
    private boolean hasIcon;

    public Hunter(String type, int era, boolean isFinal, boolean hasIcon) {
        super(type, era, isFinal);
        this.hasIcon = hasIcon;
    }

    public Hunter(Hunter source) {
        super(source);
        this.hasIcon = source.hasIcon;
    }

    @Override
    public AbstractCard clone() {
        return new Hunter(this);
    }

    @Override
    public void onPick(Player player) {

    }
}
