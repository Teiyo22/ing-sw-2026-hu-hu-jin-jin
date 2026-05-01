package it.polimi.ingsw.controller.common.messages;

import it.polimi.ingsw.controller.client.ClientController;

public abstract  class Response extends Message {
    public Response(int clientID) {
        this.clientID = clientID;
    }

    public abstract void receive(ClientController clientController);
}
