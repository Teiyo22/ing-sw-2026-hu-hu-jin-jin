package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.building.cardPick.CardVisitor;

public interface Visitable {
    void accept(CardVisitor v);
}
