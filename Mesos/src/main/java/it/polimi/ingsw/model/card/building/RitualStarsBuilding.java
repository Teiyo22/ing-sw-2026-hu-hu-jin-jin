package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class RitualStarsBuilding extends AbstractBuilding {
    private int bonusStars;

    public RitualStarsBuilding(String type, int era, boolean isFinal,
                               int cost, int pp, BuildingHandler buildingHandler,
                               int bonusStars) {
        super(type, era, isFinal, cost, pp, buildingHandler);
        this.bonusStars = bonusStars;
    }

    @Override
    public void onPick(Player player) {

    }
}
