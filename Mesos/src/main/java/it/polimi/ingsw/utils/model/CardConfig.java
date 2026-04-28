package it.polimi.ingsw.utils.model;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;

public class CardConfig {
    @Expose private AbstractCard card;
    @Expose private int[] quantity;

    public AbstractCard getCard() { return card; }

    public int[] getQuantity() { return quantity; }

}
