package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualNoLossBuilding extends AbstractBuilding{
    public RitualNoLossBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public RitualNoLossBuilding(RitualNoLossBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new RitualNoLossBuilding(this);
    }

    /**
     * Enables the no PP loss modifier for shamanic rituals.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        owner.enableNoLossRitualMod();
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
