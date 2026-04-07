package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OrderSlot;

import java.util.ArrayList;

public class ShamanicRitual extends AbstractEvent {
    private final int bonusPP;
    private final int malusPP;

    public ShamanicRitual(int era, int bonusPP, int malusPP) {
        super(era);
        this.bonusPP = bonusPP;  //must be positive
        this.malusPP = malusPP;  //must be positive
    }

    /** Apply the effects of the shamanic ritual event:
     * when called the methdìod calculates the number of stars owned by the player with the least and most.
     * Then it iterates and adds/sutracts pp if the player is amongst the ones with the most/least.*/
    @Override
    public void onEvent(Game game) {
        int minStars = 0;  //number of stars owned by the player(s) who has the least
        int maxStars = 0;  //number of stars owned by the player(s) who has the most

        OrderSlot[] order = game.getBoard().getOrderTile();

        //calulate minStars and maxStars
        for(int i=0; i<order.length; i++){
            player = order[i].getAssignedPlayer();
            playerStars = player.getTribe().getStars();
            if (playerStars < minStars){ minStars = playerStars; }
            else if (playerStars > maxStars){ maxStars = playerStars; }
        }
        //apply effects to players, depending on their number of stars
        for(int i=0; i<order.length; i++){
            player = order[i].getAssignedPlayer();
            playerStars = player.getTribe().getStars();
            if (playerStars == minStars){
                if(!player.getNoLossRitualMod()) {
                    player.addPP(-malusPP);  //players with the least stars lose pp
                }
            } else if (playerStars == maxStars){  //players with the most stars gain pp
                if(player.getDoubleRitualMod()){
                    player.addPP(bonusPP * 2);
                } else {
                    player.addPP(bonusPP);
                }
            }
        }
    }
}
