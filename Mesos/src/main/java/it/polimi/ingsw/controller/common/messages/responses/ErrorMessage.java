package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ErrorMessage extends Response implements Serializable {
    private String context;
    private List<String> errors;

    public ErrorMessage(String context, String... errors){
        this.type = MessageType.ERROR;
        this.context = context;
        this.errors = Arrays.asList(errors);
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showError(this);
    }

    public String getContext() {
        return context;
    }

    public List<String> getErrors() {
        return errors;
    }
}
