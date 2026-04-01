package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class RitualStarsBuilding extends AbstractBuilding {
    private int bonusStars;

    public RitualStarsBuilding(int era, int cost, int pp, BuildingHandler buildingHandler, int bonusStars) {
        super(era, cost, pp, buildingHandler);
        this.bonusStars = bonusStars;
    }

    @Override
    public void onPick(Player player) {

    }
}
