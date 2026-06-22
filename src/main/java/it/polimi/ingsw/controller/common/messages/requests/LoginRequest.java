package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class LoginRequest extends Request {
    String username;

    public LoginRequest(String clientID, String username) {
        this.clientID = clientID;
        this.username = username;
    }

    @Override
    public void receive(ServerController serverController) {
        serverController.login(clientID, username);
    }
}
