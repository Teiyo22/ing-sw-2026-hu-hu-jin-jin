package it.polimi.ingsw.model.card;

public interface VisitableBuilding{
    void accept(BuildingVisitor v);
}
