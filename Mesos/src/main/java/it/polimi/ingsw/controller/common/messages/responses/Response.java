package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

public abstract  class Response {
    private int clientID;

    public Response(int clientID){
        this.clientID = clientID;
    }

    public int getClientID(){
        return clientID;
    }

    public void receive(ClientController clientController){

    }
}
