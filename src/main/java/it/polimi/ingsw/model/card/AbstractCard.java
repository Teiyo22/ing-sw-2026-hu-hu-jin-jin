package it.polimi.ingsw.model.card;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.board.Row;

import java.io.Serializable;

public abstract class AbstractCard implements Serializable {
    protected int ID = -1;

    @Expose protected String resourceName;
    @Expose protected int era;
    @Expose protected boolean isFinal;

    public AbstractCard(int era, boolean isFinal) {
        this.era = era;
        this.isFinal = isFinal;
    }

    /**
     * Copy constructor for AbstractCard.
     * It is used to create copies of cards during the initialization of the game.
     *
     * @param source is the card template from which the new card is created.
     * */
    public AbstractCard(AbstractCard source) {
        this.ID = source.ID;
        this.resourceName = source.resourceName;
        this.era = source.era;
        this.isFinal = source.isFinal;
    }

    /**
     * Returns a copy of the card.
     * */
    public abstract AbstractCard clone();

    /**
     * Moves the card to the specified row.
     * Depending on the card type, different methods in {@link Row} are called.
     *
     * @param row is the row where the card is moved.
     * */
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(obj == this) return true;

        if(obj.getClass() != this.getClass()) return false;

        return ((AbstractCard) obj).getID() == this.ID;
    }

    public String getResource() {
        return resourceName;
    }

    @Override
    public String toString() {
        String format = " %-10s | %-26s | %-15s ";
        String id = String.format("ID: %d", ID);
        String ERA = String.format("Era: %d", era);

        return String.format(format, id, this.getClass().getSimpleName(), ERA);
    }
}
