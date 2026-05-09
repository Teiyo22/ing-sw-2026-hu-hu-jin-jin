package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualDoubleBonusBuilding extends AbstractBuilding {
    public RitualDoubleBonusBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public RitualDoubleBonusBuilding(RitualDoubleBonusBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new RitualDoubleBonusBuilding(this);
    }

    /**
     * Enables the bonus PP doubling modifier for shamanic rituals.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        owner.enableDoubleRitualMod();
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
