package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.Visitable;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Player;

public abstract class CardPickBuilding extends AbstractBuilding implements CardVisitor {
    public CardPickBuilding(String type, int era, boolean isFinal,
                            int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public CardPickBuilding(CardPickBuilding source) {
        super(source);
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addCardPickBuilding(this);
    }

    public void applyEffect(Visitable v, Player p) {
        if (p == owner) v.accept(this);
    }

    @Override public void doForArtist(Artist artist) {}
    @Override public void doForBuilder(Builder builder) {}
    @Override public void doForCollector(Collector collector) {}
    @Override public void doForHunter(Hunter hunter) {}
    @Override public void doForShaman(Shaman shaman) {}
    @Override public void doForInventor(Inventor inventor) {}
}
