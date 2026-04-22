package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.board.OrderSlot;

public class OrderTileBuilding extends AbstractBuilding{
    public OrderTileBuilding(String type, int era, boolean isFinal, int cost, int pp) {
        super(type, era, isFinal, cost, pp);
    }

    public OrderTileBuilding(OrderTileBuilding source) {
        super(source);
    }
    @Override

    public AbstractCard clone() {
        return new OrderTileBuilding(this);
    }

    /**
     * Adds the card to the list of order tile buildings in the building handler.
     * */
    @Override
    public void onPick(Player player, BuildingHandler buildingHandler) {
        super.onPick(player, buildingHandler);
        buildingHandler.addOrderTileBuilding(this);

    }

    /**
     * The effect is applied when the owner is assigned to an order slot.
     * If the order slot in which the owner is assigned provides a food bonus, the owner gets 1 extra food.
     * */
    public void applyEffect(OrderSlot slot) {
        if(slot.getAssignedPlayer() == owner && slot.getFoodDelta() > 0){
            owner.addFood(1);
        }
    }
}
