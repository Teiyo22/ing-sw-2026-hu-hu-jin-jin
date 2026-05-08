package it.polimi.ingsw.controller.client.turn;

import it.polimi.ingsw.model.player.Player;

public abstract class TurnState {
    protected Player currPlayer;
    protected int index;

    public abstract boolean canPickCard();
    public abstract boolean canPickOffer();
    public abstract boolean hasEnded();

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public int getIndex() {
        return index;
    }
}
