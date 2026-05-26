package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.common.info.CardPickStateInfo;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.action.CardPickPlayerAction;
import it.polimi.ingsw.model.action.OfferPickPlayerAction;
import it.polimi.ingsw.model.player.Player;

public abstract class GameState {
    final protected Game game;
    final protected BuildingHandler buildingHandler;

    public GameState(Game game, BuildingHandler buildingHandler) {
        this.game = game;
        this.buildingHandler = buildingHandler;
    }

    public abstract void update();

    public BuildingHandler getBuildingHandler() {
        return buildingHandler;
    }

    public abstract ModelStateInfo getModelStateInfo();

    public String[] validate(CardPickPlayerAction action) {
        return new String[]{"This action is not available"};
    }

    public String[] validate(OfferPickPlayerAction action) {
        return new String[]{"This action is not available"};
    }
}
