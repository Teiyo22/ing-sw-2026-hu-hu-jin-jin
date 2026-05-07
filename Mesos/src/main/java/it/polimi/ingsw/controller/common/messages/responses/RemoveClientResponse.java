package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class RemoveClientResponse extends Response {
    private int lobbyID;

    public RemoveClientResponse(int clientID, int lobbyID){
        super(clientID);
        this.type = MessageType.REMOVE_CLIENT;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.removeClient(super.getClientID(), lobbyID);
    }
}
