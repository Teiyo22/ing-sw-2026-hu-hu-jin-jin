package it.polimi.ingsw.controller.client.turn;

import it.polimi.ingsw.model.player.Player;

public class CardPickState extends TurnState {
    public CardPickState(Player currPlayer, int index, int era) {
        super(currPlayer, index, era);
    }

    @Override
    public boolean canPickCard() {
        return true;
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
