package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionState extends GameState {
    private Player currPlayer = null;
    private int solvedExtraActions=0;

    public ExtraActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    public void pickTop(Pickable p) {
        p.onPick(currPlayer, buildingHandler);
        p.removeFrom(game.getBoard().getTopRow());
        solvedExtraActions++;
        update();
    }

    public void setCurrPlayer(Player player){
       currPlayer = player;
    }

    @Override
    public void update() {
        buildingHandler.applyExtraActionEffects(this, solvedExtraActions);
        if(currPlayer==null) {
            game.setGameState(new RoundEndState(game, buildingHandler));
            game.getGameState().update();
        }
    }
}
