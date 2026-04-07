package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OrderSlot;

import java.util.ArrayList;

public class Sustenance extends AbstractEvent {
    private final int ppMultiplier;

    public Sustenance(int era, int ppMultiplier) {
        super(era);
        this.ppMultiplier = ppMultiplier;
    }

    @Override
    public void moveTo(Row row){
        row.addSustenanceEvent(this);
    }


    /** Apply the effects of the sustenance event:
     * subtracts food to feed every member of a player's tribe.
     * If there is not enough food it subtracts pp.*/
    @Override
    public void onEvent(Game game) {
        OrderSlot[] order = game.getBoard().getOrderTile();

        for(int i=0; i<order.length; i++) {  //apply the effects for each player
            player = order[i].getAssignedPlayer();
            //get the number of tribe members
            int tribeSize = player.getTribe().getTribeSize();
            tribeSize -= player.getTribe().getSustenanceDiscount();  //discount caused by collectors and buildings
            if(tribeSize>0) {
                //if there is not enough food to feed every member subtract pp for every remaining member
                if (tribeSize > player.getFood()) {
                    tribeSize -= player.getFood();  //calculate number of unfed members
                    player.setFood(0);  //spend all the food
                    player.addPP(-tribeSize * ppMultiplier);  //lose pp
                } else {
                    player.addFood(-tribeSize);  //otherwise just remove the needed amount of food, 1 per member
                }
            }
        }
    }
}
