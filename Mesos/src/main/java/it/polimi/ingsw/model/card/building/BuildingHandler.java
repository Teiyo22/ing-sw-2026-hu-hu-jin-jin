package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.card.Visitable;
import it.polimi.ingsw.model.card.building.cardPick.CardPickBuilding;
import it.polimi.ingsw.model.card.building.gameEnd.GameEndBuilding;
import it.polimi.ingsw.model.gameState.ExtraActionState;
import it.polimi.ingsw.model.player.Player;
import java.util.*;

import java.util.List;

public class BuildingHandler {
    private List<CardPickBuilding> cardPickBuildings;
    private List<HuntBuilding> huntBuildings;
    private List<CavePaintingBuilding> cavePaintingBuildings;
    private List<OrderTileBuilding> orderTileBuildings;
    private List<ExtraActionBuilding> extraActionBuildings;
    private List<GameEndBuilding> gameEndBuildings;

    public BuildingHandler() {
        this.cardPickBuildings = new ArrayList<>();
        this.huntBuildings = new ArrayList<>();
        this.cavePaintingBuildings = new ArrayList<>();
        this.orderTileBuildings = new ArrayList<>();
        this.extraActionBuildings = new ArrayList<>();
        this.gameEndBuildings = new ArrayList<>();
    }

    public void addCardPickBuilding(CardPickBuilding building){
        cardPickBuildings.add(building);
    }

    public void addHuntBuilding(HuntBuilding building){
        huntBuildings.add(building);
    }

    public void addCavePaintingBuilding(CavePaintingBuilding building){
        cavePaintingBuildings.add(building);
    }

    public void addOrderTileBuilding(OrderTileBuilding building){
        orderTileBuildings.add(building);
    }

    public void addGameEndBuilding(GameEndBuilding building){
        gameEndBuildings.add(building);
    }

    public void addExtraActionBuilding(ExtraActionBuilding building){
        extraActionBuildings.add(building);
    }

    /**
     * After a card has been picked, apply the effects of the card pick buildings.
     * The effects consist in receiving different bonuses depending on the building and picked card.
     * */
    public void applyCardPickEffects(Visitable visitable, Player player) {
        for(CardPickBuilding building: cardPickBuildings){
            building.applyEffect(visitable, player);
        }
    }

    /**
     * During the hunt event, apply the effects of the hunt buildings.
     * The effects consist in receiving a bonus food and bonus PP depending on the number of hunters.
     * */
    public void applyHuntEffects() {
        for(HuntBuilding building: huntBuildings){
            building.applyEffect();
        }
    }

    /**
     * During the cave painting event, apply the effects of the cave painting buildings.
     * The effects consist in receiving a bonus food depending on the number of artists.
     * */
    public void applyCavePaintingEffects() {
        for(CavePaintingBuilding building: cavePaintingBuildings){
            building.applyEffect();
        }

    }

    /**
     * Apply the effects of the order tile buildings.
     * The effects consist in receiving a bonus PP depending on the order tile.
     * */
    public void applyOrderTileEffects(OrderSlot slot) {
        for(OrderTileBuilding building: orderTileBuildings){
            building.applyEffect(slot);
        }
    }

    /**
     * After all offers are resolved, apply the effects of the extra action buildings.
     * Multiple extra action buildings can be present.
     * Extra action state passes to round end state if all extra actions are resolved.
     * */
    public void applyExtraActionEffects(ExtraActionState state, int buildingIndex) {
        if(buildingIndex < extraActionBuildings.size()){
            extraActionBuildings.get(buildingIndex).applyEffect(state);
        } else {
            state.setCurrPlayer(null);
        }
    }

    /**
     * At the end of the game, apply the effects of all game end buildings.
     * Game end buildings effects consist in receiving bonus PP.
     * */
    public void applyGameEndEffects() {
        for(GameEndBuilding building: gameEndBuildings){
            building.applyEffect();
        }
    }
}
