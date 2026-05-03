package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Response;

public class ErrorMessage extends Response {
    String errorMessage;

    public ErrorMessage(int clientID, String errorMessage){
        super(clientID);
        this.errorMessage = errorMessage;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.handleError(super.getClientID(), errorMessage);
    }
}
