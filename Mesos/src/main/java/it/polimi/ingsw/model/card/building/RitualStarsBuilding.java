package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualStarsBuilding extends AbstractBuilding {
    private int bonusStars;

    public RitualStarsBuilding(String type, int era, boolean isFinal,
                               int cost, int pp, BuildingHandler buildingHandler,
                               int bonusStars) {
        super(type, era, isFinal, cost, pp, buildingHandler);
        this.bonusStars = bonusStars;
    }

    public RitualStarsBuilding(RitualStarsBuilding source) {
        super(source);
        this.bonusStars = source.bonusStars;
    }

    @Override
    public AbstractCard clone() {
        return new RitualStarsBuilding(this);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        owner.getTribe().addStars(3);
    }
}
