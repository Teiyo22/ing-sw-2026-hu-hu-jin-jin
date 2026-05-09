package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class BonusPPBuilding extends AbstractBuilding implements VisitableBuilding {
    @Expose
    private int bonusPP;

    public BonusPPBuilding(String type, int era, boolean isFinal, int cost, int pp, int bonusPP) {
        super(type, era, isFinal, cost, pp);
        this.bonusPP = bonusPP;
    }

    public BonusPPBuilding(BonusPPBuilding source) {
        super(source);
        this.bonusPP = source.bonusPP;
    }

    @Override
    public AbstractCard clone() {
        return new BonusPPBuilding(this);
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    public int getBonusPP() {
        return bonusPP;
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addGameEndBuilding(this);
    }

    @Override
    public String toString() {
        String format = " %-10s | %-26s | %-15s | %-15s | %-15s | %-25s ";
        String ID = String.format("ID: %d", getID());
        String ERA = String.format("Era: %d", super.getEra());
        String COST = String.format("Cost: %d", super.getCost());
        String PP = String.format("PP: %d", super.getPP());
        String BONUSPP = String.format("BonusPP: %d", bonusPP);

        return String.format(format, ID, type, ERA, COST, PP, BONUSPP);
    }
}