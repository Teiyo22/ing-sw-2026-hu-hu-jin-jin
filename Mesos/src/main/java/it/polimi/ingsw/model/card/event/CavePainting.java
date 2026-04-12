package it.polimi.ingsw.model.card.event;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OrderSlot;

import java.util.ArrayList;

public class CavePainting extends AbstractEvent{
    private final int bonusPP;  //must be a positive number
    private final int malusPP;  //must be a negative number
    private int numArtistsBonus;
    private int numArtistsMalus;

    public CavePainting(int era, int bonusPP, int malusPP, int  numArtistsBonus, int numArtistsMalus) {
        super(era);
        this.bonusPP = bonusPP;
        this.malusPP = malusPP;
        this.numArtistsBonus = numArtistsBonus;
        this.numArtistsMalus = numArtistsMalus;
    }

    /** Apply the effects of the cave painting event:
     * when called the method subtracts or adds pp depending on the number of artists the player owns.*/
    @Override
    public void onEvent(Game game) {
        OrderSlot[] order = game.getBoard().getOrderTile();

        for(int i = 0; i < order.length ; i++){  //apply effects for each player
            Player player = order[i].getAssignedPlayer;
            int numArtists = player.getTribe().getArtistCount();
            if(numArtists <= numArtistsMalus){
                player.addPP(-malusPP);
            } else if (numArtists >= numArtistsBonus){
                player.addPP(bonusPP * numArtists);
            }
        }

        game.getGameState().getBuildingHandler().applyCavePaintingEffects();
    }
}
