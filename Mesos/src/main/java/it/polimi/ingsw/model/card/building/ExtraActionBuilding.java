package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.BuildingVisitor;
import it.polimi.ingsw.model.card.VisitableBuilding;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionBuilding extends AbstractBuilding implements VisitableBuilding {
    public ExtraActionBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public ExtraActionBuilding(ExtraActionBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new ExtraActionBuilding(this);
    }

    /**
     * Adds the card to the list of extra actions buildings in the building handler.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addExtraActionBuilding(this);
    }

    @Override
    public void accept(BuildingVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        String format = " %-10s | %-26s | %-15s | %-15s | %-15s ";
        String ID = String.format("ID: %d", getID());
        String ERA = String.format("Era: %d", super.getEra());
        String COST = String.format("Cost: %d", super.getCost());
        String PP = String.format("PP: %d", super.getPP());

        return String.format(format, ID, type, ERA, COST, PP);
    }
}
