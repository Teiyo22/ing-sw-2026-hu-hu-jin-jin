package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class WaitingLobbyRequest extends Request {
    public WaitingLobbyRequest(int clientID) {
        super(clientID);
        this.type = MessageType.WAITING_LOBBY;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.getWaitingLobbies(super.getClientID());
    }

}
