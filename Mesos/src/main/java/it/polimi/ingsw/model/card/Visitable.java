package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;

public interface Visitable {
    public abstract void accept(CardVisitor v);
}
