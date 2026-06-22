package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Response;

public class SetIDResponse extends Response {
    private String clientID;

    public SetIDResponse(String clientID){
        this.clientID = clientID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.setID(clientID);
    }
}
