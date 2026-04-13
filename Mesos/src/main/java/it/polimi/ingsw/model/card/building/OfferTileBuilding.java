package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class OfferTileBuilding extends AbstractBuilding{
    public OfferTileBuilding(String type, int era, boolean isFinal,
                             int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    @Override
    public void onPick(Player player) {

    }
}
