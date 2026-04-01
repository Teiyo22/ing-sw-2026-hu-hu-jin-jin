package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.building.BuildingHandler;

public abstract class GameState {
    final protected Game game;
    final protected BuildingHandler buildingHandler;

    public GameState(Game game, BuildingHandler buildingHandler) {
        this.game = game;
        this.buildingHandler = buildingHandler;
    }

    public abstract void update();
}
