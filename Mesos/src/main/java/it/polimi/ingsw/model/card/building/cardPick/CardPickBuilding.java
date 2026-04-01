package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public abstract class CardPickBuilding extends AbstractBuilding implements CardVisitor {
    public CardPickBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void onPick(Player player) {

    }
}
