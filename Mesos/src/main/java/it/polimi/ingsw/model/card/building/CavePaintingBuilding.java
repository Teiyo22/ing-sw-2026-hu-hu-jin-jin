package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class CavePaintingBuilding extends AbstractBuilding{
    private int bonusFood;

    public CavePaintingBuilding(int era, int cost, int pp, BuildingHandler buildingHandler, int bonusFood) {
        super(era, cost, pp, buildingHandler);
        this.bonusFood = bonusFood;
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addCavePaintingBuilding();
    }

    public void applyEffect() {
        owner.addFood(owner.getTribe().getArtistCount());
    }
}
