package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.board.Row;

public abstract class AbstractCard {
    protected String type;
    protected int era;
    protected boolean isFinal;

    public AbstractCard(String type, int era, boolean isFinal) {
        this.type = type;
        this.era = era;
        this.isFinal = isFinal;
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
