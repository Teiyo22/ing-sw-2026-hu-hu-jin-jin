package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class ExtraActionState extends GameState {
    private Player currPlayer = null;
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
}
