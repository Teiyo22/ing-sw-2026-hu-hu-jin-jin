package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;

import java.util.Optional;

public class TUIHideAction implements Action {
    final private ClientController clientController;
    private Lobby currLobby;

    public TUIHideAction(ClientController clientController) {
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
    public boolean parseAction(String[] args) {
        clientController.hidePlayer();
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}

