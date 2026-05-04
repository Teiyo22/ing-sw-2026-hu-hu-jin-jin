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
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addGameEndBuilding(this);
    }

    @Override
    public String toString() {
        return String.format("[ Type: %s  |  Era: %d  |  Cost: %d  |  PP: %d  |  BonusPP: %d ]",
                super.getClass(), super.getEra(), super.getCost(), super.getPP(), bonusPP);
    }
}