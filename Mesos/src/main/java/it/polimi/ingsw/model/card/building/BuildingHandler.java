package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.Pickable;
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

    public void addCardPickBuildings(CardPickBuilding building){
        cardPickBuildings.add(building);

        return;
    }

    public void addHuntBuildings(HuntBuilding building){
        huntBuildings.add(building);

        return;
    }

    public void addCavePaintingBuildings(CavePaintingBuilding building){
        cavePaintingBuildings.add(building);

        return;
    }
    public void addOrderTileBuildings(OrderTileBuilding building){
        orderTileBuildings.add(building);

        return;
    }
    public void addGameEndBuildings(GameEndBuilding building){
        gameEndBuildings.add(building);

        return;
    }
    public void addExtraActionBuildings(ExtraActionBuilding building){
        extraActionBuildings.add(building);

        return;
    }

    public void applyCardPickEffects(Pickable pickable, Player player) {
        for(CardPickBuilding building: cardPickBuildings){
            building.applyEffect(pickable, player);
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
        if(buildingIndex<extraActionBuildings.size()){
            ExtraActionBuilding building  = extraActionBuildings.get(buildingIndex);
            building.applyEffect(state);
        }else{
            state.setCurrPlayer(null);
        }
    }

    public void applyGameEndEffects() {
        for(GameEndBuilding building: gameEndBuildings){
            building.applyEffect();
        }
    }
}
