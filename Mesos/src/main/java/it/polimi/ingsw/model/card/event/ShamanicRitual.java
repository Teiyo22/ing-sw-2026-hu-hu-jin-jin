package it.polimi.ingsw.model.card.event;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class ShamanicRitual extends AbstractEvent {
    @Expose private int bonusPP;
    @Expose private int malusPP;

    public ShamanicRitual(String type, int era, boolean isFinal, int bonusPP, int malusPP) {
        super(type, era, isFinal);
        this.bonusPP = bonusPP;
        this.malusPP = malusPP;
    }

    public ShamanicRitual(ShamanicRitual source) {
        super(source);
        this.bonusPP = source.bonusPP;
        this.malusPP = source.malusPP;
    }


    @Override
    public AbstractCard clone() {
        return new ShamanicRitual(this);
    }

    /**
     * Depending on the number of minimum and maximum numbers of stars owned by the players,
     * subtract/add pp to the players who have a number of stars equal to the minimum/maximum.
     * */
    @Override
    public void onEvent(Game game) {
        List<Player> players = game.getPlayers();

        int minStars = players.getFirst().getTribe().getStars();
        int maxStars = minStars;  //number of stars owned by the player(s) who has the most

        for(int i = 1; i < players.size(); i++){
            int stars = players.get(i).getTribe().getStars();

            if (stars < minStars)
                minStars = stars;
            else if (stars > maxStars)
                maxStars = stars;
        }

        //apply effects to players, depending on their number of stars
        for(Player player: players){
            int playerStars = player.getTribe().getStars();

            if (playerStars == minStars){
                if(!player.getNoLossRitualMod()) {
                    player.addPP(-malusPP);  //players with the least stars lose pp
                }
            }

            if (playerStars == maxStars){  //players with the most stars gain pp
                if(player.getDoubleRitualMod()){
                    player.addPP(bonusPP * 2);
                } else {
                    player.addPP(bonusPP);
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                "[ ID: %-3d |  %-20s  |  Era: %-3d  |  Malus PP: %-3d | Bonus PP: %-3d ]",
                getID(), super.getClass().getSimpleName(), super.getEra(), -malusPP, bonusPP);
    }
}
