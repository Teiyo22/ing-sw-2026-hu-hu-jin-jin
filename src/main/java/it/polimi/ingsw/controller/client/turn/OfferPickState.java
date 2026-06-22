package it.polimi.ingsw.controller.client.turn;

import it.polimi.ingsw.model.player.Player;

public class OfferPickState extends TurnState {
    public OfferPickState(Player currPlayer, int index, int era) {
        super(currPlayer, index, era);
    }

    @Override
    public boolean canPickCard() {
        return false;
    }

    @Override
    public boolean canPickOffer() {
        return true;
    }

    @Override
    public boolean hasEnded() {
        return false;
    }
}
