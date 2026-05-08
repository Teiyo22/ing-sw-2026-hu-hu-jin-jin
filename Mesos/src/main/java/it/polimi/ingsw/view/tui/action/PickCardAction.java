package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;

import java.util.Optional;

public class PickCardAction implements Action {
    final private ClientController clientController;

    public PickCardAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "4";
    }

    @Override
    public String label() {
        return "Card";
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        return Optional.empty();
    }

    public void handlePickCard() {
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] {Top Row ID ...} {Bottom Row ID ...}", key(), label());
    }
}
