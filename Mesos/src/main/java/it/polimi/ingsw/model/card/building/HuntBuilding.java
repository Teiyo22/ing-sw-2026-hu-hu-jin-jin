package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.event.Hunt;
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

    public HuntBuilding(HuntBuilding source) {
        super(source);
        this.bonusPP = source.bonusPP;
        this.bonusFood = source.bonusFood;
    }

    @Override
    public AbstractBuilding clone() {
        return new HuntBuilding(this);
    }

    @Override
    public void onPick(Player player) {
        super.onPick(player);
        buildingHandler.addHuntBuilding(this);
    }

    public void applyEffect() {
        owner.addFood(owner.getTribe().getHunterCount()*bonusFood);
        owner.addPP(owner.getTribe().getHunterCount()*bonusPP);
    }
}
