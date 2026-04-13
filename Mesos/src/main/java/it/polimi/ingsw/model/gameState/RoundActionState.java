package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RoundActionState extends GameState{
    private Player currPlayer;
    private int solvedOffers = 0;
    private int i=0;
    private OrderSlot[] orderTile;
    private OfferTile[] offers;
    private List<OfferTile> turnOrder = new ArrayList<>();

    public RoundActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);

        this.offers = game.getBoard().getOfferTrack();
        for(i=0; i<offers.size(); i++) {
            if (offers[i].getAssignedPlayer() != null) {
                turnOrder.add(offers[i]);
            }
        }
        this.orderTile = game.getBoard().getOrderTile();
    }

    @Override
    public void update() {
        if(solvedOffers == game.getPlayers.size()){
            game.setGameState(new ExtraActionState(game, buildingHandler));
            game.getGameState().update();
        }

        currPlayer = turnOrder.get(solvedOffers).getAssignedPlayer();
    }

    public void setPlayer(Player player){
        currPlayer = player;
    }


    private void pick(Pickable p, Row row, int removedIndex) {
        p.onPick(currPlayer);
        p.remove(row, removedIndex);

        assignToOrderSlot(currPlayer, turnOrder.get(solvedOffers));
    }


    public void assignToOrderSlot(Player player, OfferTile tile) {
        orderTile[i].setPlayer(player);
        tile.setPlayer(null);
        solvedOffers++;
        buildingHandler.applyOrderTileEffects(orderTile[i]);
        i++;
        update();
    }
}
