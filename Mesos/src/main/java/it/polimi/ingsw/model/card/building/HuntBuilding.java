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

    public HuntBuilding(int era, boolean isFinal,
                        int cost, int pp, int bonusPP, int bonusFood) {
        super(era, isFinal, cost, pp);
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
    public void register(Player player, BuildingHandler buildingHandler) {
        super.register(player, buildingHandler);
        if (buildingHandler !=  null)
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
        String format = "| %-25s | %-25s ";
        String BONUSPP = String.format("Bonus PP: %d", bonusPP);
        String BONUSFOOD = String.format("Bonus Food: %d", bonusFood);

        return super.toString() + String.format(format, BONUSPP, BONUSFOOD);
    }
}
