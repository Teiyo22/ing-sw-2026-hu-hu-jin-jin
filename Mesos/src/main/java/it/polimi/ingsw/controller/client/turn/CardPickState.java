package it.polimi.ingsw.controller.client.turn;

import it.polimi.ingsw.model.player.Player;

public class CardPickState extends TurnState {
    public CardPickState(Player currPlayer, int index) {
        super(currPlayer, index);
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
