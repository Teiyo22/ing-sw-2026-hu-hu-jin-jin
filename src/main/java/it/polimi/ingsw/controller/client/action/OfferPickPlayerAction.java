package it.polimi.ingsw.controller.client.action;

import it.polimi.ingsw.model.Game;

public class OfferPickPlayerAction extends PlayerAction {
    private int offerIndex;

    public OfferPickPlayerAction(int offerIndex) {
        this.offerIndex = offerIndex;
    }

    @Override
    public String[] canExecute(Game game) {
        return game.getGameState().validate(this);
    }

    @Override
    public void execute(Game game) {
        game.assignTo(player, offerIndex);
    }

    public int getOfferIndex() {
        return offerIndex;
    }
}
