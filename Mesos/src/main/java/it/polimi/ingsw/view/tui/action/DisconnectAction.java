package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.DisconnectCommand;

import java.util.Optional;

public class DisconnectAction implements Action {
    final private ClientController clientController;

    public DisconnectAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "0";
    }

    @Override
    public String label() {
        return "Disconnect";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        new DisconnectCommand().execute(clientController);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}
