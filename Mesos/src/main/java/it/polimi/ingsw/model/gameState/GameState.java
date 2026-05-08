package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public abstract class GameState {
    final protected Game game;
    final protected BuildingHandler buildingHandler;

    Player currPlayer = null;

    public GameState(Game game, BuildingHandler buildingHandler) {
        this.game = game;
        this.buildingHandler = buildingHandler;
    }

    public BuildingHandler getBuildingHandler() {
        return buildingHandler;
    }

    public abstract void update();

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public abstract ModelStateInfo getModelStateInfo();
}
