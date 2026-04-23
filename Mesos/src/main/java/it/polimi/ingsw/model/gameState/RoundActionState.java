package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.card.Pickable;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class RoundActionState extends GameState{
    private Player currPlayer;
    private final OfferTile[] offerTrack;

    private int solvedOffers = 0;
    private int assignedPlayers = 0;

    public RoundActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
        offerTrack = game.getBoard().getOfferTrack();
    }

    /**
     * Finds the next offer tile to be resolved and sets the player assigned to it as current player.
     * If the offer tile also provides bonus food, the player gains the specified amount.
     * If all offer tiles have been resolved, the state changes to {@link ExtraActionState}.
     * */
    @Override
    public void update() {
        for(; solvedOffers < offerTrack.length && offerTrack[solvedOffers].getAssignedPlayer() == null; solvedOffers++);

        if(offerTrack.length == solvedOffers){
            game.setGameState(new ExtraActionState(game, buildingHandler));
            game.getGameState().update();
            return;
        }

        currPlayer = offerTrack[solvedOffers].getAssignedPlayer();
        offerTrack[solvedOffers].solveBonusFood();
    }

    public void setPlayer(Player player){
        currPlayer = player;
    }


    private void pick(List<Pickable> topPicks, List<Pickable> bottomPicks) {
        for(Pickable p: topPicks){
            p.onPick(currPlayer, buildingHandler);
            p.removeFrom(game.getBoard().getTopRow());
        }

        for(Pickable p: bottomPicks){
            p.onPick(currPlayer, buildingHandler);
            p.removeFrom(game.getBoard().getBottomRow());
        }

        assignToOrderSlot(offerTrack[solvedOffers]);
    }


    /**
     * After a player has finished his action turn, he is assigned to the correct order slot.
     * */
    public void assignToOrderSlot(OfferTile offerTile) {
        OrderSlot orderSlot = game.getBoard().getOrderTile()[assignedPlayers];

        orderSlot.setPlayer(offerTile.getAssignedPlayer());
        orderSlot.solveDeltaFood();

        offerTile.setPlayer(null);
        buildingHandler.applyOrderTileEffects(orderSlot);

        solvedOffers++;
        assignedPlayers++;
        update();
    }
}
