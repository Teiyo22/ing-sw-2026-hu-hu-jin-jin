package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;

import java.util.Optional;

public class PickOfferAction implements Action {
    final private ClientController clientController;

    public PickOfferAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "3";
    }

    @Override
    public String label() {
        return "Offer";
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <Offer ID>", key(), label());
    }
}
