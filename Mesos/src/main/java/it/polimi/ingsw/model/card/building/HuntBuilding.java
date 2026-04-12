package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class HuntBuilding extends AbstractBuilding{
    private int bonusPP;
    private int bonusFood;

    public HuntBuilding(int era, int cost, int pp, BuildingHandler buildingHandler, int bonusPP, int bonusFood) {
        super(era, cost, pp, buildingHandler);
        this.bonusPP = bonusPP;
        this.bonusFood = bonusFood;
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addHuntBuilding();
    }

    public void applyEffect() {
        owner.addFood(owner.getTribe().getHunterCount()*bonusFood);
        owner.addPP(owner.getTribe().getHunterCount()*bonusPP);
    }
}
