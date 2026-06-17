package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.controller.client.action.CardPickPlayerAction;
import it.polimi.ingsw.controller.client.action.OfferPickPlayerAction;

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

    public static GameState getGameState(Game game) {
        GameState gameState = isRoundStart(game.getBoard().getOrderTile())
            ? new RoundStartState(game, game.getBuildingHandler())
            : new RoundActionState(game, game.getBuildingHandler());

        gameState.update();

        return gameState;
    }

    private static boolean isRoundStart(OrderSlot[] orderTile) {
        if (orderTile[0].getAssignedPlayer() != null) {
            for (OrderSlot orderSlot : orderTile)
                if (orderSlot.getAssignedPlayer() == null)
                    return false;

            return true;
        } else {
            for (OrderSlot orderSlot : orderTile)
                if (orderSlot.getAssignedPlayer() != null)
                    return true;

            return false;
        }
    }
}
