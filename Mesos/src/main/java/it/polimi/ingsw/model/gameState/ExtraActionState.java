package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class ExtraActionState extends GameState {
    private Player currPlayer = null;

    public ExtraActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }



    public void pickTop(Pickable p) {
        p.onPick(currPlayer);
        game.setGameState(new RoundEndState);
    }

    @Override
    public void update() {

    }
}
