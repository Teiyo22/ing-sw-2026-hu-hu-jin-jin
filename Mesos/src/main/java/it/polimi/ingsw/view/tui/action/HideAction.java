package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;

import java.util.Optional;

public class HideAction implements Action {
    final private ClientController clientController;
    private Lobby currLobby;

    public HideAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "2";
    }

    @Override
    public String label() {
        return "Hide";
    }

    @Override
    public boolean isEnabled() {
        currLobby = clientController.getCurrLobby();
        return currLobby != null && currLobby.isShownPlayer();
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        currLobby.hidePlayer();
        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}

