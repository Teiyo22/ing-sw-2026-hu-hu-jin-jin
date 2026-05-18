package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.command.StartLobbyCommand;

import java.util.Optional;

public class StartLobbyAction implements Action {
    final private ClientController clientController;

    public StartLobbyAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "6";
    }

    @Override
    public String label() {
        return "Start";
    }

    @Override
    public boolean isEnabled() {
        Lobby currLobby = clientController.getCurrLobby();
        return currLobby != null &&
               currLobby.containsClient(clientController.getID()) &&
               currLobby.getPlayerCount() == currLobby.getSize();
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        new StartLobbyCommand(clientController).execute();
         return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}
