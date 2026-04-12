package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

public class RoundActionState extends GameState{
    private Player currPlayer;
    private int solvedOffers = 0;
    private int i=0;
    private OrderSlot[] orderTile;

    public RoundActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);

        this.orderTile = game.getBoard().getOrderTile();
    }

    //metodo chiamato nel controller dopo che si finisce di eseguire una tessera offerta
    @Override
    public void update() {
        if(solvedOffers == game.getOfferTile().size()){
            onEnd();
        }
        solvedOffers++;

    }

    public void setPlayer(Player player){
        currPlayer = player;
    }

    @Override
    public void onEnd(){

        game.setGameState(new ExtraActionState());
    }


    private void pick(Pickable p, Row row) {
        p.onPick(currPlayer);
        //togliere carta dalla row
    }



    public void assignToOrderSlot(Player player) {
        //resettare l'offerTile
        orderTile[i].setPlayer(player);
        buildingHandler.applyOrderTileEffects(i, orderTile);
        i++;
    }
}
