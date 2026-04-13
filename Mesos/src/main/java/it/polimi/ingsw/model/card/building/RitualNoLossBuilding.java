package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualNoLossBuilding extends AbstractBuilding{
    public RitualNoLossBuilding(String type, int era, boolean isFinal,
                                int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    public RitualNoLossBuilding(RitualNoLossBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new RitualNoLossBuilding(this);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        owner.setNoLossRitualMod(true);
    }
}
