package it.polimi.ingsw.controller.client.turn;

public class EndState extends TurnState {
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
        return true;
    }
}
