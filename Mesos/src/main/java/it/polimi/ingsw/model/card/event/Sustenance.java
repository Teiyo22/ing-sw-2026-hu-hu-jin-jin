package it.polimi.ingsw.model.card.event;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;

import java.util.List;


public class Sustenance extends AbstractEvent {
    @Expose
    private int ppMultiplier;

    public Sustenance(String type, int era, boolean isFinal, int ppMultiplier) {
        super(type, era, isFinal);
        this.ppMultiplier = ppMultiplier;
    }

    public Sustenance(Sustenance source) {
        super(source);
        this.ppMultiplier = source.ppMultiplier;
    }

    @Override
    public AbstractCard clone() {
        return new Sustenance(this);
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
        List<Player> players = game.getPlayers();

        for(Player player: players) {  //apply the effects for each player
            //get the number of tribe members
            int foodCost = Math.max(player.getTribe().getTribeSize() - player.getTribe().getSustenanceDiscount(), 0);
            int unfedCount =  foodCost - player.getFood();

            if (unfedCount > 0) {
                player.setFood(0);  //spend all the food
                player.addPP(unfedCount * ppMultiplier);  //lose pp
            } else {
                player.addFood(-foodCost);  //otherwise just remove the needed amount of food, 1 per member
            }
        }
    }
}
