package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Response;

public class PingResponse extends Response {
    @Override
    public void receive(ClientController clientController) {
        clientController.ping();
    }
}
