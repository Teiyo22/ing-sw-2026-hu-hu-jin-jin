package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.turn.TurnState;

public class TUIShowEndAction implements TUIAction {
    final private ClientController clientController;

    public TUIShowEndAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "6";
    }

    @Override
    public String label() {
        return "End";
    }

    @Override
    public boolean isEnabled() {
        TurnState turnState = clientController.getCurrLobby().getTurnState();
        return turnState != null && turnState.hasEnded();
    }

    @Override
    public boolean parseAction(String[] args) {
        clientController.showEnd();
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", key(), label());
    }
}
