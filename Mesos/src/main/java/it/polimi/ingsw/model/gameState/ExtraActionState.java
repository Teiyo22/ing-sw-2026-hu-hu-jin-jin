package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionState extends GameState {
    private Player currPlayer = null;

    public ExtraActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
        buildingHandler.applyExtraActionEffects(this);
        if(currPlayer==null) {
            game.setGameState(new RoundEndState(game, buildingHandler));
            game.getGameState().update();
        }
    }

    public void pickTop(Pickable p, int removedIndex) {
        p.onPick(currPlayer);
        p.remove(game.getBoard().getTopRow(), removedIndex);
        update();
    }

    public void setCurrPlayer(Player player){
       currPlayer = player;
    }

    @Override
    public void update() {
        game.setGameState(new RoundEndState(game, buildingHandler));
        game.getGameState().update();
    }
}
