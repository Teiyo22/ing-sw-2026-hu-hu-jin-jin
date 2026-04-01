package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;

public class Hunt extends AbstractEvent {
    private final int ppMultiplier;

    public Hunt(int era, int ppMultiplier) {
        super(era);
        this.ppMultiplier = ppMultiplier;
    }

    @Override
    public void onEvent(Game game) {
    }
}
