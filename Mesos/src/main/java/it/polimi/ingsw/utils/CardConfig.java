package it.polimi.ingsw.utils;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;

public class CardConfig {
    @Expose private AbstractCard card;
    @Expose private int quantity;
    @Expose private int requiredPlayers;

    public AbstractCard getCard() { return card; }

    public Integer getQuantity() { return quantity; }

    public Integer getRequiredPlayers() { return requiredPlayers; }
}
