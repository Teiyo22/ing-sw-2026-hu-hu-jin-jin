package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;

public abstract class Request {
    private int clientID;

    public abstract void receive(ServerController serverController);
}
