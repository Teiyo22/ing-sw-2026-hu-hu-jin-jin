package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.command.StartLobbyCommand;

public class TUIStartLobbyAction implements TUIAction {
    final private ClientController clientController;

    public TUIStartLobbyAction(ClientController clientController) {
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
    public boolean parseAction(String[] args) {
        new StartLobbyCommand(clientController).execute();
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}
