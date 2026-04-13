package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;

public class CavePainting extends AbstractEvent{
    private final int bonusPP;
    private final int malusPP;

    public CavePainting(String type, int era, boolean isFinal, int bonusPP, int malusPP) {
        super(type, era, isFinal);
        this.bonusPP = bonusPP;
        this.malusPP = malusPP;
    }

    @Override
    public void onEvent(Game game) {

    }
}
