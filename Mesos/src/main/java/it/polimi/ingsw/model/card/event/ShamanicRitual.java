package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;

public class ShamanicRitual extends AbstractEvent {
    private int bonusPP;
    private int malusPP;

    public ShamanicRitual(String type, int era, boolean isFinal, int bonusPP, int malusPP) {
        super(type, era, isFinal);
        this.bonusPP = bonusPP;
        this.malusPP = malusPP;
    }

    public ShamanicRitual(ShamanicRitual source) {
        super(source);
        this.bonusPP = source.bonusPP;
        this.malusPP = source.malusPP;
    }

    @Override
    public AbstractCard clone() {
        return new ShamanicRitual(this);
    }

    @Override
    public void onEvent(Game game) {

    }
}
