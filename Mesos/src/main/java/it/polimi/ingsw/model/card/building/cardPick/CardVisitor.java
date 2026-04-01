package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.character.*;

public interface CardVisitor {
    public abstract void doForInventor(Inventor i);
    public abstract void doForShaman(Shaman s);
    public abstract void doForHunter(Hunter h);
    public abstract void doForCollector(Collector c);
    public abstract void doForArtist(Artist a);
    public abstract void doForBuilder(Builder b);
}
