package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.controller.client.info.OfferPickStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.controller.client.action.OfferPickPlayerAction;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RoundStartState extends GameState {
    private Player currPlayer = null;
    private int assignedSlots = 0;

    public RoundStartState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    /**
     * Sets the current player based on the number of already assigned players.
     * If all players have been assigned, the state changes to {@link RoundActionState}.
     * */
    @Override
    public void update() {
        OrderSlot[] orderTile = game.getBoard().getOrderTile();

        if(currPlayer != null) {
            orderTile[assignedSlots].setPlayer(null);
        }

        for (; assignedSlots < orderTile.length && orderTile[assignedSlots].getAssignedPlayer() == null; assignedSlots++);

        if(assignedSlots == orderTile.length){
            game.setGameState(new RoundActionState(game, buildingHandler));
            game.getGameState().update();

            return;
        }

        currPlayer = orderTile[assignedSlots].getAssignedPlayer();
    }

    @Override
    public ModelStateInfo getModelStateInfo(){
        return new OfferPickStateInfo(currPlayer, assignedSlots, game.getBoard().getDeck().getCurrentEra());
    }

    @Override
    public String[] validate(OfferPickPlayerAction action) {
        List<String> errors = new ArrayList<>();

        if (!action.getPlayer().equals(currPlayer))
            errors.add("Actions are only allowed during your turn");

        if (action.getOfferIndex() < 0 || action.getOfferIndex() >= game.getBoard().getOfferTrack().length)
            errors.add("Invalid offer index");
        else if (game.getBoard().getOfferTrack()[action.getOfferIndex()].getAssignedPlayer() != null)
            errors.add("Offer already picked by another player");

        return errors.toArray(new String[0]);
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void setAssignedSlots(int assignedSlots) {
        this.assignedSlots = assignedSlots;
    }

    @Override
    public GameState copy() {
        RoundStartState copy = new RoundStartState(game, buildingHandler);
        copy.currPlayer = currPlayer;
        copy.assignedSlots = assignedSlots;
        return copy;
    }

    @Override
    public void fixReferences(Game game) {
        super.fixReferences(game);
        if (currPlayer != null)
            for (Player p: game.getPlayers())
                if (currPlayer.equals(p))
                    currPlayer = p;
    }
}
