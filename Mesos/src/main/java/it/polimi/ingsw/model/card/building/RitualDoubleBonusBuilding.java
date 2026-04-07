package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class RitualDoubleBonusBuilding extends AbstractBuilding {
    public RitualDoubleBonusBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        owner.setDoubleRitualMod(True);
    }
}
