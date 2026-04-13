package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;

public class Sustenance extends AbstractEvent {
    private int ppMultiplier;

    public Sustenance(String type, int era, boolean isFinal, int ppMultiplier) {
        super(type, era, isFinal);
        this.ppMultiplier = ppMultiplier;
    }

    public Sustenance(Sustenance source) {
        super(source);
        this.ppMultiplier = source.ppMultiplier;
    }

    @Override
    public AbstractCard clone() {
        return new Sustenance(this);
    }

    @Override
    public void onEvent(Game game) {

    }
}
