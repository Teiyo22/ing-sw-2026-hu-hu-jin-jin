package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualDoubleBonusBuilding extends AbstractBuilding {
    public RitualDoubleBonusBuilding(String type, int era, boolean isFinal,
                                     int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp, buildingHandler);
    }

    public RitualDoubleBonusBuilding(RitualDoubleBonusBuilding source) {
        super(source);
    }

    @Override
    public AbstractCard clone() {
        return new RitualDoubleBonusBuilding(this);
    }

    @Override
    public void onPick(Player player) {
        super.onPick(player);
        owner.setDoubleRitualMod(true);
    }
}
