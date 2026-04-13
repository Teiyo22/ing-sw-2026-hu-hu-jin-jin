package it.polimi.ingsw.model.card.building.gameEnd;

import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public abstract class GameEndBuilding extends AbstractBuilding {
    public GameEndBuilding(String type, int era, boolean isFinal,
                           int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public GameEndBuilding(GameEndBuilding source) {
        super(source);
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addGameEndBuilding(this);
    }

    public abstract void applyEffect();
}
