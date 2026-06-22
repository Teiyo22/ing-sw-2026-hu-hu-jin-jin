package it.polimi.ingsw.controller.client.turn;

import it.polimi.ingsw.model.player.Player;

public abstract class TurnState {
    protected Player currPlayer;
    protected int index;
    protected int era;

    public TurnState(Player currPlayer, int index, int era){
        this.currPlayer = currPlayer;
        this.index = index;
        this.era = era;
    }

    public abstract boolean canPickCard();
    public abstract boolean canPickOffer();
    public abstract boolean hasEnded();

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public int getIndex() {
        return index;
    }

    public int getEra() {
        return era;
    }
}
