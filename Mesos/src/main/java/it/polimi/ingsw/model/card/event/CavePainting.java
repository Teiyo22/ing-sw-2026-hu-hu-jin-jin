package it.polimi.ingsw.model.card.event;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class CavePainting extends AbstractEvent{
    @Expose private int bonusPP;  //must be a positive number
    @Expose private int malusPP;  //must be a negative number
    @Expose private int numArtistsBonus;
    @Expose private int numArtistsMalus;

    public CavePainting(String type, int era, boolean isFinal, 
                        int bonusPP, int malusPP, int numArtistsBonus, int numArtistsMalus) {
        super(type, era, isFinal);
        this.bonusPP = bonusPP;
        this.malusPP = malusPP;
        this.numArtistsBonus = numArtistsBonus;
        this.numArtistsMalus = numArtistsMalus;
    }

    public CavePainting(CavePainting source) {
        super(source);
        this.bonusPP = source.bonusPP;
        this.malusPP = source.malusPP;
        this.numArtistsBonus = source.numArtistsBonus;
        this.numArtistsMalus = source.numArtistsMalus;
    }


    @Override
    public AbstractCard clone() {
        return new CavePainting(this);
    }

    /**
     * Add/subtracts pp depending on the number of artists the player owns.
     * If the number of artists is lower than or equal to {@code numArtistsMalus}, the player loses pp.
     * Else if the number of artists is greater than or equal to {@code numArtistsBonus}, the player gains pp.
     * Otherwise the player does not gain/lose pp.
     * */
    @Override
    public void onEvent(Game game) {
        List<Player> players = game.getPlayers();

        for(Player player: players){  //apply effects for each player
            int numArtists = player.getTribe().getArtistCount();

            if(numArtists <= numArtistsMalus){
                player.addPP(-malusPP);
            } else if (numArtists >= numArtistsBonus){
                player.addPP(bonusPP * numArtists);
            }
        }

        game.getGameState().getBuildingHandler().applyCavePaintingEffects();
    }

    @Override
    public String toString() {
        return String.format(
                "[ ID: %-3d |  %-20s  |  Era: %-3d  |  Malus PP: %-3d |  Malus Threshold: %-3d | Bonus PP: %-3d |  Bonus Threshold: %-3d ]",
                getID(), super.getClass().getSimpleName(), super.getEra(), -malusPP, numArtistsMalus, bonusPP, numArtistsBonus);
    }
}
