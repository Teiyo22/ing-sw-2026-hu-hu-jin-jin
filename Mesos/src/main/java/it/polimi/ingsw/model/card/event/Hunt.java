package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OrderSlot;

import java.util.ArrayList;

public class Hunt extends AbstractEvent {
    private  int ppMultiplier;

    public Hunt(String type, int era, boolean isFinal, int ppMultiplier) {
        super(type, era, isFinal);
        this.ppMultiplier = ppMultiplier;
    }

    public Hunt(Hunt source) {
        super(source);
        this.ppMultiplier = source.ppMultiplier;
    }

    @Override
    public AbstractCard clone() {
        return new Hunt(this);
    }


    /** Apply the effects of the hunting event:
     * when called the method adds food and pp depending on the number of hunters the player owns.*/
    @Override
    public void onEvent(Game game) {
        OrderSlot[] order = game.getBoard().getOrderTile();

        for(int i=0; i<order.length; i++){  //apply effects for each player
            Player player = order[i].getAssignedPlayer();
            int numHunters = player.getTribe().getHunterCount();
            player.addFood(numHunters);
            player.addPP(numHunters * ppMultiplier);
        }

        game.getGameState().getBuildingHandler().applyHuntEffects();
    }
}
