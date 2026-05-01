package it.polimi.ingsw.controller.common.messages;

import it.polimi.ingsw.controller.server.ServerController;

public abstract class Request extends Message {
    public Request(int clientID) {
        this.clientID = clientID;
    }

    public abstract void receive(ServerController serverController);
}
