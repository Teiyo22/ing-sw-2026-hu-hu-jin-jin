package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.card.Pickable;
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

    public void applyCardPickEffects(Visitable visitable, Player player) {
        for(CardPickBuilding building: cardPickBuildings){
            building.applyEffect(visitable, player);
        }
    }

    public void applyHuntEffects() {
        for(HuntBuilding building: huntBuildings){
            building.applyEffect();
        }
    }

    public void applyCavePaintingEffects() {
        for(CavePaintingBuilding building: cavePaintingBuildings){
            building.applyEffect();
        }

    }

    public void applyOrderTileEffects(OrderSlot slot) {
        for(OrderTileBuilding building: orderTileBuildings){
            building.applyEffect(slot);
        }
    }

    public void applyExtraActionEffects(ExtraActionState state, int buildingIndex) {
        if(buildingIndex < extraActionBuildings.size()){
            extraActionBuildings.get(buildingIndex).applyEffect(state);
        } else {
            state.setCurrPlayer(null);
        }
    }

    public void applyGameEndEffects() {
        for(GameEndBuilding building: gameEndBuildings){
            building.applyEffect();
        }
    }
}
