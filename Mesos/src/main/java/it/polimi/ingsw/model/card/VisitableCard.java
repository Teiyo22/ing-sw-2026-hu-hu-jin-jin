package it.polimi.ingsw.model.card;

public interface VisitableCard {
    void accept(CardVisitor v);
}
