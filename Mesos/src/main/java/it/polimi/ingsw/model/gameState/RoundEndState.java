package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.building.BuildingHandler;

public class RoundEndState extends GameState {
    public RoundEndState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    @Override
    public void update() {

    }

    private void resolveEvents(Row row) {

    }

    private void setUp() {

    }

    private void redrawCards() {

    }

    private void changeAge() {

    }
}
