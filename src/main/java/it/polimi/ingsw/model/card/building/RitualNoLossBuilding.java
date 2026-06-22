package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualNoLossBuilding extends AbstractBuilding {
    public RitualNoLossBuilding(int era, boolean isFinal, int cost, int pp) {
        super(era, isFinal, cost, pp);
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
     *
     */
    @Override
    public void register(Player player, BuildingHandler buildingHandler) {
        super.register(player, buildingHandler);
        owner.enableNoLossRitualMod();
    }
}