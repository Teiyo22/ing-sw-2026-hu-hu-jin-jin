package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualDoubleBonusBuilding extends AbstractBuilding {
    public RitualDoubleBonusBuilding(int era, boolean isFinal, int cost, int pp) {
        super(era, isFinal, cost, pp);
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
        return String.format("[ Type: %s  |  Era: %d  |  Cost: %d  |  PP: %d ]",
                super.getClass(), super.getEra(), super.getCost(), super.getPP());
    }
}
