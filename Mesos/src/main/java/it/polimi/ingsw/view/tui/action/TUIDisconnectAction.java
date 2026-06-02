package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.DisconnectCommand;

public class TUIDisconnectAction implements TUIAction {
    final private ClientController clientController;

    public TUIDisconnectAction(ClientController clientController) {
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
    public boolean parseAction(String[] args) {
        new DisconnectCommand(clientController).execute();
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}
