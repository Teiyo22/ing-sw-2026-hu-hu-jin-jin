package it.polimi.ingsw.controller.client.turn;

public class OfferPickState extends TurnState {
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
