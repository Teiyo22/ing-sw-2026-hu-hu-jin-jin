package it.polimi.ingsw.model.card.building;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

public class RitualStarsBuilding extends AbstractBuilding {
    @Expose private int bonusStars;

    public RitualStarsBuilding(String type, int era, boolean isFinal,
                               int cost, int pp, int bonusStars) {
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


    /**
     * Adds 3 stars to the tribe of the player who picked the card.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        owner.getTribe().addStars(3);
    }

    @Override
    public String toString() {
        return String.format("[ Type: %s  |  Era: %d  |  Cost: %d  |  PP: %d  |  Bonus Stars: %d ]",
                super.getType(), super.getEra(), super.getCost(), super.getPP(), bonusStars);
    }
}
