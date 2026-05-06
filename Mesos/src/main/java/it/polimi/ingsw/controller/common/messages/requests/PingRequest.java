package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class PingRequest extends Request {
    public PingRequest(int clientID) {
        super(clientID);
        this.type = MessageType.PING;
    }

    @Override
    public void receive(ServerController serverController) {
        serverController.ping(clientID);
    }
}
