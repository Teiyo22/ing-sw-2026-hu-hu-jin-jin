package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class ErrorMessage extends Response {
    private String errorMessage;

    public ErrorMessage(String errorMessage){
        this.type = MessageType.ERROR;
        this.errorMessage = errorMessage;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showError(errorMessage);
    }
}
