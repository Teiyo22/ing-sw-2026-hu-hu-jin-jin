package it.polimi.ingsw.model.card.event;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.controller.client.EventResult;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;

import java.util.List;


public class Sustenance extends AbstractEvent {
    @Expose
    private int ppMultiplier;

    public Sustenance(int era, boolean isFinal, int ppMultiplier) {
        super(era, isFinal);
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


    /**
     * Subtracts food to all players based on the size of their tribe.
     * If food is not enough, then a certain amount of pp is subtracted depending on the number of unfed tribesmen.
     * */
    @Override
    public void onEvent(Game game) {
        List<Player> players = game.getPlayers();
        List<EventResult> results = players.stream()
            .map(EventResult::new)
            .toList();

        game.getBuildingHandler().applySustenanceEffect();

        for(Player player: players) {  //apply the effects for each player
            //get the number of tribe members
            int foodCost = Math.max(player.getTribe().getTribeSize() - player.getTribe().getSustenanceDiscount(), 0);
            int unfedCount =  foodCost - player.getFood();

            if (unfedCount > 0) {
                player.setFood(0);  //spend all the food
                player.addPP(-unfedCount * ppMultiplier);  //lose pp
            } else {
                player.addFood(-foodCost);  //otherwise just remove the needed amount of food, 1 per member
            }
        }

        results.forEach(EventResult::computeDelta);
        game.getLobbyState().notifyEventResolution("Sustenance", results);
    }

    @Override
    public String toString() {
        String format = "| %-25s ";
        String PPMULTIPLIER = String.format("PPMultiplier: %d", ppMultiplier);

        return super.toString() + String.format(format, PPMULTIPLIER);
    }
}
