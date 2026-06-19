package it.polimi.ingsw.controller.common.messages;

import it.polimi.ingsw.controller.client.ClientController;

public abstract class Response {
    public abstract void receive(ClientController clientController);
}
