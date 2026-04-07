package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class OrderTileBuilding extends AbstractBuilding{
    public OfferTileBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void onPick(Player player) {

    }
}
