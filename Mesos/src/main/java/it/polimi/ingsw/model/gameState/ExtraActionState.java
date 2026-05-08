package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.common.info.CardPickStateInfo;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionState extends GameState {
    private int solvedExtraActions = -1;

    public ExtraActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    public void setCurrPlayer(Player player){
       currPlayer = player;
    }

    /**
     * Applies the effects of extra action buildings which effectively initialize the player for the extra action.
     * If no player is found, then pass to {@link RoundEndState}
     * */
    @Override
    public void update() {
        solvedExtraActions++;
        buildingHandler.applyExtraActionEffects(this, solvedExtraActions);

        if(currPlayer == null) {
            game.setGameState(new RoundEndState(game, buildingHandler));
            game.getGameState().update();
        }
    }

    @Override
    public ModelStateInfo getModelStateInfo() {
        return new CardPickStateInfo(currPlayer, -1);
    }
}
