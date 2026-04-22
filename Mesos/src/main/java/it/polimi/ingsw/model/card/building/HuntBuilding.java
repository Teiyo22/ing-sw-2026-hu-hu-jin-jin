package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class HuntBuilding extends AbstractBuilding{
    @Expose private int bonusPP;
    @Expose private int bonusFood;

    public HuntBuilding(String type, int era, boolean isFinal,
                        int cost, int pp, int bonusPP, int bonusFood) {
        super(type, era, isFinal, cost, pp);
        this.bonusPP = bonusPP;
        this.bonusFood = bonusFood;
    }

    public HuntBuilding(HuntBuilding source) {
        super(source);
        this.bonusPP = source.bonusPP;
        this.bonusFood = source.bonusFood;
    }

    @Override
    public AbstractCard clone() {
        return new HuntBuilding(this);
    }

    /**
     * Adds the card to the list of hunt buildings in the building handler.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addHuntBuilding(this);
    }

    /**
     * The effect is applied during hunt events.
     * Add bonus food and bonus PP to the owner depending on the number of hunters.
     * */
    public void applyEffect() {
        owner.addFood(owner.getTribe().getHunterCount()*bonusFood);
        owner.addPP(owner.getTribe().getHunterCount()*bonusPP);
    }
}
