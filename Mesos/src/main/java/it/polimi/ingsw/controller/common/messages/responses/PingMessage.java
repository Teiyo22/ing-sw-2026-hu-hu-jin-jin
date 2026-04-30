package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

public class PingMessage extends Response {
    public PingMessage(int clientID) {
        super(clientID);
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.ping();
    }
}
