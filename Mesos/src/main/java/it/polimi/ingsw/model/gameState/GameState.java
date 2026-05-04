package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.common.GameStateInfo.GameStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.tui.TUIView;

public abstract class GameState {
    final protected Game game;
    final protected BuildingHandler buildingHandler;

    GameStateInfo gameStateInfo;

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

    public GameStateInfo getGameStateInfo(){
        return gameStateInfo;
    }
}
