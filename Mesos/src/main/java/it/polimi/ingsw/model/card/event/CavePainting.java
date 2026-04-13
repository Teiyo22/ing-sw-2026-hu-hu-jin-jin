package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;

public class CavePainting extends AbstractEvent{
    private final int bonusPP;
    private final int malusPP;

    public CavePainting(String type, int era, boolean isFinal, int bonusPP, int malusPP) {
        super(type, era, isFinal);
        this.bonusPP = bonusPP;
        this.malusPP = malusPP;
    }

    public CavePainting(CavePainting source) {
        super(source);
        this.bonusPP = source.bonusPP;
        this.malusPP = source.malusPP;
    }

    @Override
    public AbstractCard clone() {
        return new CavePainting(this);
    }

    @Override
    public void onEvent(Game game) {

    }
}
