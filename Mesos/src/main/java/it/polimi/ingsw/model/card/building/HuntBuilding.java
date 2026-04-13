package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class HuntBuilding extends AbstractBuilding{
    private int bonusPP;
    private int bonusFood;

    public HuntBuilding(String type, int era, boolean isFinal,
                        int cost, int pp, BuildingHandler buildingHandler,
                        int bonusPP, int bonusFood) {
        super(type, era, isFinal, cost, pp, buildingHandler);
        this.bonusPP = bonusPP;
        this.bonusFood = bonusFood;
    }

    @Override
    public void onPick(Player player) {

    }

    public void applyEffect() {

    }
}
