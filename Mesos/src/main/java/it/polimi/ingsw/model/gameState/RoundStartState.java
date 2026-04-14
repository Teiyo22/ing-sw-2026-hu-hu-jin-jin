package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class RoundStartState extends GameState {
    private Player currPlayer;
    private int assignedSlots = 0;

    public RoundStartState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    @Override
    public void update() {
        if(assignedSlots == game.getPlayers().size()){
            game.setGameState(new RoundActionState(game, buildingHandler));
            game.getGameState().update();
            return;
        }

        currPlayer = game.getPlayers().get(assignedSlots);
    }


    public void assignTo(OfferTile offer){
        game.getBoard().getOrderTile()[assignedSlots].setPlayer(null);
        offer.setPlayer(currPlayer);

        assignedSlots++;
        update();
    }
}
