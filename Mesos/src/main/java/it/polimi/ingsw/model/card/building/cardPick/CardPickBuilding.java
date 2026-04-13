package it.polimi.ingsw.model.card.building.cardPick;

import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public abstract class CardPickBuilding extends AbstractBuilding implements CardVisitor {
    public CardPickBuilding(String type, int era, boolean isFinal,
                            int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    public CardPickBuilding(CardPickBuilding source) {
        super(source);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addCardPickBuilding(this);
    }
}
