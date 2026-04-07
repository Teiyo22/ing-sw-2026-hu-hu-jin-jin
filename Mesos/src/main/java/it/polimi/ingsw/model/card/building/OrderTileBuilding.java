package it.polimi.ingsw.model.card.building;

import it.polimi.ingsw.model.player.Player;

public class OrderTileBuilding extends AbstractBuilding{
    public OrderTileBuilding(int era, int cost, int pp, BuildingHandler buildingHandler) {
        super(era, cost, pp, buildingHandler);
    }

    @Override
    public void onPick(Player player) {
        owner = player;
        buildingHandler.addOfferTileBuilding();
    }

    public void applyEffect(OrderSlot[] orderTile) {
        for(int i=0; i<orderTile.length - 1; i++){
            if(orderTile[i].getAssignedPlayer() == owner && orderTile[i].getFoodDelta() > 0){
                owner.addFood(1);
                return;
            }
        }
    }
}
