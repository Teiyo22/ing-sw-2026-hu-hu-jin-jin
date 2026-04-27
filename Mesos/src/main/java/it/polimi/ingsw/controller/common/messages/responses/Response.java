package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Message;

public abstract  class Response extends Message {
    public Response(int clientID) {
        this.clientID = clientID;
    }

    public abstract void receive(ClientController clientController);
}
