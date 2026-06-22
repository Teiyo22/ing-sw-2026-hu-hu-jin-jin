package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.controller.client.action.CardPickPlayerAction;
import it.polimi.ingsw.controller.client.action.OfferPickPlayerAction;

public abstract class GameState {
    transient protected Game game;
    transient BuildingHandler buildingHandler;

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

    public abstract GameState copy();

    public void fixReferences(Game game) {
        this.game = game;
        this.buildingHandler = game.getBuildingHandler();
    }
}
