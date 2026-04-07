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


    //metodo chiamato nel controller dopo che si finisce di eseguire una tessera offerta
    @Override
    public void update() {
        solvedOffers++;
        if(solvedOffers == game.getOfferTile().size){
            onEnd();
        }
    }

    @Override
    public void onEnd(){
        game.setGameState(new ExtraActionState);
    }

    private void pick(Pickable p, Row row) {
        p.onPick(currPlayer);
    }

    public void assignToOrderSlot(Player player, int i) {
        OrderSlot[] orderSlots = game.getBoard().getOfferTile();
        orderSlots[i].setPlayer(player);

        buildingHandler.applyOrderTileEffects(orderSlots);
    }
}
