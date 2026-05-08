package it.polimi.ingsw.controller.client.turn;

public class CardPickState extends TurnState {
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
