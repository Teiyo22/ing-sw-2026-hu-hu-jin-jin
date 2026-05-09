package it.polimi.ingsw.model.card.event;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class Hunt extends AbstractEvent {
    @Expose private int ppMultiplier;

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

    /**
     * Adds food and pp depending on the number of hunters the player owns.
     * */
    @Override
    public void onEvent(Game game) {
        List<Player> players = game.getPlayers();

        for(Player player: players){  //apply effects for each player
            int numHunters = player.getTribe().getHunterCount();
            player.addFood(numHunters);
            player.addPP(numHunters * ppMultiplier);
        }

        game.getGameState().getBuildingHandler().applyHuntEffects();
    }

    @Override
    public String toString() {
        String format = " %-10s | %-26s | %-15s | %-25s ";
        String ID = String.format("ID: %d", getID());
        String ERA = String.format("Era: %d", super.getEra());
        String PPMULTIPLIER = String.format("PPMultiplier: %d", ppMultiplier);

        return String.format(format, ID, type, ERA, PPMULTIPLIER);
    }
}
