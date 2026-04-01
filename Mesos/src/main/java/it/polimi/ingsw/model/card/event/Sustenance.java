package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;

public class Sustenance extends AbstractEvent {
    private final int ppMultiplier;

    public Sustenance(int era, int ppMultiplier) {
        super(era);
        this.ppMultiplier = ppMultiplier;
    }

    @Override
    public void onEvent(Game game) {

    }
}
