package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class RoundActionState extends GameState{
    private Player currPlayer;
    private int solvedOffers = 0;

    public RoundActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);

    }

    @Override
    public void update() {

    }

    private void pick(Pickable p, Row row) {

    }

    public void assignToOrderSlot(Player player) {

    }

}
