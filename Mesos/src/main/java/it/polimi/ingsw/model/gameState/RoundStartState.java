package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.common.GameStateInfo.GameStateInfo;
import it.polimi.ingsw.controller.common.GameStateInfo.OfferPickState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.view.view.ViewStates;

public class RoundStartState extends GameState {
    private int assignedSlots = -1;

    public RoundStartState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    /**
     * Sets the current player based on the number of already assigned players.
     * If all players have been assigned, the state changes to {@link RoundActionState}.
     * */
    @Override
    public void update() {
        if(currPlayer != null)
            game.getBoard().getOrderTile()[assignedSlots].setPlayer(null);

        assignedSlots++;

        if(assignedSlots == game.getPlayers().size()){
            game.setGameState(new RoundActionState(game, buildingHandler));
            game.getGameState().update();

            return;
        }

        currPlayer = game.getPlayers().get(assignedSlots);
        game.getLobbyController().roundStartView();
    }

    @Override
    public GameStateInfo getGameStateInfo(){
        return new OfferPickState(currPlayer);
    }
}
