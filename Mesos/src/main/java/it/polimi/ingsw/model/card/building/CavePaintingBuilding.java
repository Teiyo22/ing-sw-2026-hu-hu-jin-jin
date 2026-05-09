package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class CavePaintingBuilding extends AbstractBuilding implements VisitableBuilding {
    @Expose private int bonusFood;

    public CavePaintingBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, int bonusFood) {
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

    /**
     * Adds the cave painting building to the player's building handler.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addCavePaintingBuilding(this);
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    public int getBonusFood() {
        return bonusFood;
    }

    @Override
    public String toString() {
        String format = " %-10s | %-26s | %-15s | %-15s | %-15s | %-25s ";
        String ID = String.format("ID: %d", getID());
        String ERA = String.format("Era: %d", super.getEra());
        String COST = String.format("Cost: %d", super.getCost());
        String PP = String.format("PP: %d", super.getPP());
        String BONUSFOOD = String.format("BonusPP: %d", bonusFood);

        return String.format(format, ID, type, ERA, COST, PP, BONUSFOOD);
    }
}
