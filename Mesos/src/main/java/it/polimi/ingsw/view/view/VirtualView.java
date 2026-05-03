package it.polimi.ingsw.view.view;

import it.polimi.ingsw.controller.client.ClientController;

public abstract class VirtualView {
    private final ClientController clientController;

    public VirtualView(ClientController clientController) {
        this.clientController = clientController;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public abstract void update();
    public abstract void transitionTo(ViewStates newState);

}
