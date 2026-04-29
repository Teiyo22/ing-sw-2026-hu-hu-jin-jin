package it.polimi.ingsw.controller.client.view.TUI.states;

import it.polimi.ingsw.controller.client.ClientController;

public abstract class ViewState {
    private final ClientController controller;

    public ViewState(ClientController controller) {
        this.controller = controller;
    }

    public ClientController getController() {
        return controller;
    }

    public abstract void render();
    public abstract void handleInput(String input);
}
