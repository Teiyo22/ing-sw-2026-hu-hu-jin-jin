package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;

public class WaitingLobbyRequest extends Request{
    public WaitingLobbyRequest(int clientID) {
        super(clientID);
    }

    @Override
    public void receive(ServerController serverController){
        serverController.getWaitingLobbies(super.getClientID());
    }

}
