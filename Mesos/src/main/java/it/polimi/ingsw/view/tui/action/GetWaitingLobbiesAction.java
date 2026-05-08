package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.GetWaitingLobbiesCommand;

import java.util.Optional;

public class GetWaitingLobbiesAction implements Action {
    final private ClientController clientController;

    public GetWaitingLobbiesAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "1";
    }

    @Override
    public String label() {
        return "List";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        new GetWaitingLobbiesCommand().execute(clientController);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}
