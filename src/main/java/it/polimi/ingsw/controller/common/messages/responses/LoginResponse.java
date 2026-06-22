package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Response;

public class LoginResponse extends Response {
    private String username;

    public LoginResponse(String username){
        this.username = username;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.confirmLogin(username);
    }
}
