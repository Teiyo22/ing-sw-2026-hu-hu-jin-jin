package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.character.*;

public interface CardVisitor {
    void doForInventor(Inventor i);
    void doForShaman(Shaman s);
    void doForHunter(Hunter h);
    void doForCollector(Collector c);
    void doForArtist(Artist a);
    void doForBuilder(Builder b);
}
