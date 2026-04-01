package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class GameEndState extends GameState {
    public GameEndState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    @Override
    public void update() {

    }

    private void resolveEvents(Row row) {

    }

    private void assignBonusPP(Player p) {

    }

    private void setLeaderboard() {

    }


}
