package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class SetIDResponse extends Response {
    public SetIDResponse(int clientID){
        super(clientID);
        this.type = MessageType.SET_ID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.setID(clientID);
    }
}
