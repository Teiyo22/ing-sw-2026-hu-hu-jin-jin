package it.polimi.ingsw.controller.client.turn;

public class IdleState extends TurnState {
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
