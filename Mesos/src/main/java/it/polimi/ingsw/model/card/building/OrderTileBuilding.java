package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.board.OrderSlot;

public class OrderTileBuilding extends AbstractBuilding{
    public OrderTileBuilding(String type, int era, boolean isFinal,
                             int cost, int pp, BuildingHandler buildingHandler) {
        super(type, era, isFinal, cost, pp);
    }

    public OrderTileBuilding(OrderTileBuilding source) {
        super(source);
    }
    @Override

    public AbstractCard clone() {
        return new OrderTileBuilding(this);
    }

    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addOrderTileBuilding(this);

    }
    public void applyEffect(OrderSlot slot) {
        if(slot.getAssignedPlayer() == owner && slot.getFoodDelta() > 0){
            owner.addFood(1);
        }
    }
}
