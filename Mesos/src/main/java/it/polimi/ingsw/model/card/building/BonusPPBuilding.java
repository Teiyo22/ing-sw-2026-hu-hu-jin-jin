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

    public BonusPPBuilding(int era, boolean isFinal, int cost, int pp, int bonusPP) {
        super(era, isFinal, cost, pp);
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
    public void register(Player player, BuildingHandler buildingHandler) {
        super.register(player, buildingHandler);
        if (buildingHandler !=  null)
            buildingHandler.addGameEndBuilding(this);
    }

    @Override
    public String toString() {
        String format = "| %-25s ";
        String BONUSPP = String.format("BonusPP: %d", bonusPP);

        return super.toString() + String.format(format, BONUSPP);
    }
}