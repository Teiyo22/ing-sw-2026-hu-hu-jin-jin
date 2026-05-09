package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class HuntBuilding extends AbstractBuilding implements VisitableBuilding {
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

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    public int getBonusPP() {
        return bonusPP;
    }

    public int getBonusFood() {
        return bonusFood;
    }

    @Override
    public String toString() {
        String format = " %-10s | %-26s | %-15s | %-15s | %-15s | %-25s | %-25s ";
        String ID = String.format("ID: %d", getID());
        String ERA = String.format("Era: %d", super.getEra());
        String COST = String.format("Cost: %d", super.getCost());
        String PP = String.format("PP: %d", super.getPP());
        String BONUSPP = String.format("Bonus PP: %d", bonusPP);
        String BONUSFOOD = String.format("Bonus Food: %d", bonusFood);

        return String.format(format, ID, type, ERA, COST, PP, BONUSPP, BONUSFOOD);
    }
}
