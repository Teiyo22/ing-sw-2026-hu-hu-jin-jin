package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class CavePaintingBuilding extends AbstractBuilding{
    @Expose private int bonusFood;

    public CavePaintingBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, BuildingHandler buildingHandler,
                                int bonusFood) {
        super(type, era, isFinal, cost, pp);
        this.bonusFood = bonusFood;
    }

    public CavePaintingBuilding(CavePaintingBuilding source) {
        super(source);
        this.bonusFood = source.bonusFood;
    }

    @Override
    public AbstractCard clone() {
        return new CavePaintingBuilding(this);
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addCavePaintingBuilding(this);
    }

    public void applyEffect() {
        owner.addFood(owner.getTribe().getArtistCount()*bonusFood);
    }
}
