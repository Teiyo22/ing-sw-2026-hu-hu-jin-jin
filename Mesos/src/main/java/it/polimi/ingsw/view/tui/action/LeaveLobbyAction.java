package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.command.LeaveLobbyCommand;

import java.util.Optional;

public class LeaveLobbyAction implements Action {
    final private ClientController clientController;

    public LeaveLobbyAction(ClientController clientController) {
        this.clientController = clientController;
    }
    @Override
    public String key() {
        return "5";
    }

    @Override
    public String label() {
        return "Leave";
    }

    @Override
    public boolean isEnabled() {
        Lobby currLobby = clientController.getCurrLobby();

        return currLobby != null && currLobby.containsClient(clientController.getID());
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        new LeaveLobbyCommand(clientController).execute();
        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}
