package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.board.Row;

public abstract class AbstractCard {
    protected final int era;

    public AbstractCard(int era) {
        this.era = era;
    }

    public abstract void moveTo(Row row);
}
