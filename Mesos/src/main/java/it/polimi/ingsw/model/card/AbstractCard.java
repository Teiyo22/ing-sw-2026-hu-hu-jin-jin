package it.polimi.ingsw.model.card;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.board.Row;

public abstract class AbstractCard {
    protected int ID = -1;

    @Expose protected String type;
    @Expose protected String resourceName;
    @Expose protected int era;
    @Expose protected boolean isFinal;

    public AbstractCard(String type, int era, boolean isFinal) {
        this.type = type;
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
        this.type = source.type;
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

    public String getType() {
        return type;
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
}
