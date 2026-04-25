package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

public class SetIDResponse extends Response {
    private int ID;
    public SetIDResponse(int clientID, int ID){
        super(clientID);
        this.ID = ID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.setId(ID);
    }
}
