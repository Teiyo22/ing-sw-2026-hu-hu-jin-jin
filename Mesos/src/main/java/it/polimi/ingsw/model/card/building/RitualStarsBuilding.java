package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualStarsBuilding extends AbstractBuilding {
    @Expose private int bonusStars;

    public RitualStarsBuilding(String type, int era, boolean isFinal,
                               int cost, int pp, BuildingHandler buildingHandler,
                               int bonusStars) {
        super(type, era, isFinal, cost, pp);
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
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        owner.getTribe().addStars(3);
    }
}
