package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;

public class Sustenance extends AbstractEvent {
    private final int ppMultiplier;

    public Sustenance(String type, int era, boolean isFinal, int ppMultiplier) {
        super(type, era, isFinal);
        this.ppMultiplier = ppMultiplier;
    }

    @Override
    public void onEvent(Game game) {

    }
}
