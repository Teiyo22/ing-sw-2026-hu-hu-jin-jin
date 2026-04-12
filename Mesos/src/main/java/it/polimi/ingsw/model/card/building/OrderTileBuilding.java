package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.board.OrderSlot;

public class OrderTileBuilding extends AbstractBuilding{
    public OrderTileBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addOrderTileBuilding();
    }

    public void applyEffect(OrderSlot slot) {
        if(slot.getAssignedPlayer() == owner && slot.getFoodDelta() > 0){
            owner.addFood(1);
        }
    }
}
