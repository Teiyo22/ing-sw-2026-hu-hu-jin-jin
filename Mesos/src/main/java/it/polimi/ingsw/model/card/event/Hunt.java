package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;

public class Hunt extends AbstractEvent {
    private  int ppMultiplier;

    public Hunt(String type, int era, boolean isFinal, int ppMultiplier) {
        super(type, era, isFinal);
        this.ppMultiplier = ppMultiplier;
    }

    public Hunt(Hunt source) {
        super(source);
        this.ppMultiplier = source.ppMultiplier;
    }

    @Override
    public AbstractCard clone() {
        return new Hunt(this);
    }

    @Override
    public void onEvent(Game game) {
    }
}
