package it.polimi.ingsw.controller.client.turn;

import it.polimi.ingsw.model.player.Player;

public class IdleState extends TurnState {
    public IdleState(Player currPlayer, int index, int era) {
        super(currPlayer, index, era);
    }

    @Override
    public boolean canPickCard() {
        return false;
    }

    @Override
    public boolean canPickOffer() {
        return false;
    }

    @Override
    public boolean hasEnded() {
        return false;
    }
}
