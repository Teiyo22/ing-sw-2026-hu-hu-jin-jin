package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.card.AbstractCard;

public class CardConfig {
    private String type;
    private AbstractCard card;
    private int quantity;

    public String getType() { return type; }

    public AbstractCard getCard() { return card; }

    public Integer getQuantity() { return quantity; }
}
