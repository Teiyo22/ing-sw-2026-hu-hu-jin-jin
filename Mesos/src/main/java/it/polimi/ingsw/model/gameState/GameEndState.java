package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.card.event.AbstractEvent;
import it.polimi.ingsw.model.card.event.Sustenance;
import it.polimi.ingsw.model.player.Player;
import java.util.*;

public class GameEndState extends GameState {
    private final Row bottom;
    private final Row top;
    private final List<Player> playersList;

    public GameEndState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);

        this.playersList = game.getPlayers();
        this.bottom = game.getBoard().getBottomRow();
        this.top = game.getBoard().getTopRow();
    }
    /**
     * Resolves all remaining events in both the top and bottom rows.
     * Assigns bonus PP to each player, applies game end buildings effects, and sets the leaderboard.
     * */
    @Override
    public void update() {
        resolveEvents();

        for(Player p: playersList)
            assignBonusPP(p);

        buildingHandler.applyGameEndEffects();
        setLeaderboard();
    }

    private void resolveEvents() {
        List<AbstractEvent> events = top.getEventCards();
        for(AbstractEvent e: events){
            e.onEvent(game);
        }

        List<Sustenance> sustenance = top.getSustenanceEventCards();
        for(Sustenance s: sustenance){
            s.onEvent(game);
        }

        events = bottom.getEventCards();
        for(AbstractEvent e: events){
            e.onEvent(game);
        }

        sustenance = bottom.getSustenanceEventCards();
        for(Sustenance s: sustenance){
            s.onEvent(game);
        }

    }

    /**
     * Assigns the bonus PP provided by the tribe's builders, inventors, and artists.
     * */
    private void assignBonusPP(Player p) {
        int bonusPP = bonusPP = p.getTribe().getBuilderBonusPP() +
                                p.getTribe().getInventorBonusPP() +
                                10 * (p.getTribe().getArtistCount()/2);
        p.addPP(bonusPP);
    }

    /**
     * Computes the rankings based on the PP and food of each player.
     * */
    private void setLeaderboard() {
        Player previous;
        Player current;

        playersList.sort(null);

        current = playersList.getFirst();
        current.setRank(1);

        for(int evaluatedPlayers = 1; evaluatedPlayers < playersList.size(); evaluatedPlayers++) {
            previous = current;
            current = playersList.get(evaluatedPlayers);

            if (current.compareTo(previous) == 0) {
                current.setRank(previous.getRank());
            } else {
                current.setRank(evaluatedPlayers + 1);
            }
        }
    }
}
