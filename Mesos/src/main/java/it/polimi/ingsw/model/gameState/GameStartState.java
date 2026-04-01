package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.building.BuildingHandler;

public class GameStartState extends GameState{
    private int playerCount = 0;

    public GameStartState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    @Override
    public void update() {

    }

    private void assignPlayersToOrderTile() {

    }

    private void distributeCards() {

    }
}
