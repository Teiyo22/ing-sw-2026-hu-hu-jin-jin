package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.board.Row;

public abstract class AbstractCard {
    protected int era;
    protected boolean isFinal;

    public AbstractCard(int era) {
        this.era = era;
    }

    public abstract AbstractCard copy();
    public abstract void moveTo(Row row);

    public void setEra(int era) {
        this.era = era;
    }

    public int getEra() {
        return era;
    }

    public boolean isFinal() {
        return isFinal;
    }
}
