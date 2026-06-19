package it.polimi.ingsw.controller.common.messages;

import it.polimi.ingsw.controller.server.ServerController;

public abstract class Request {
    protected String clientID;

    public abstract void receive(ServerController serverController);
}
