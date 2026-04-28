package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.character.*;

public interface CardVisitor {
    void visit(Inventor i);
    void visit(Shaman s);
    void visit(Hunter h);
    void visit(Collector c);
    void visit(Artist a);
    void visit(Builder b);
}
