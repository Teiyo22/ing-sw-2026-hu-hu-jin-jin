package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.cardPick.CardPickBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.GameEndBuilding;
import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class BuildingHandler {
    private List<CardPickBuilding> cardPickBuildings;
    private List<HuntBuilding> huntBuildings;
    private List<CavePaintingBuilding> cavePaintingBuildings;
    private List<OrderTileBuilding> orderTileBuildings;
    private List<ExtraActionBuilding> extraActionBuildings;
    private List<GameEndBuilding> gameEndBuildings;

    public BuildingHandler() {
    }

    public void applyCardPickEffects(Pickable pickable, Player player) {

    }

    public void applyHuntEffects() {

    }

    public void applyCavePaintingEffects() {

    }

    public void applyOfferTileEffects(Player p, int foodDelta) {

    }

    public void applyExtraActionEffects(ExtraActionState state) {

    }

    public void applyGameEndEffects() {

    }
}
