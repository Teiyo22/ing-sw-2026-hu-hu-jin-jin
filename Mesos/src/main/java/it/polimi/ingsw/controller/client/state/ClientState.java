package it.polimi.ingsw.controller.client.state;

import it.polimi.ingsw.controller.client.ClientController;

public abstract class ClientState {
    protected ClientController clientController;

    public ClientState(ClientController clientController) {
        this.clientController = clientController;
    }

    public abstract void updateView();
}
