package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
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
        OrderSlot[] orderTile = game.getBoard().getOrderTile();
        for(int i=0; i<=game.){
            //...

        }


        assignedSlots++;
        if(assignedSlots == game.getPlayers().size()){
            onEnd();
        }
    }

    @Override
    public void onEnd(){
        game.setGameState(new RoundActionState());
        game.getGameState().update();
    }



    public void assignTo(OfferTile offer){
        //resettare l'oderTIle
        offer.setPlayer(currPlayer);
        update();
    }
}
