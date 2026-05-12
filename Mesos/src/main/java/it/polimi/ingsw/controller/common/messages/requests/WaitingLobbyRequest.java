package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.network.TCPClientInterface;

public class WaitingLobbyRequest extends Request {
    public WaitingLobbyRequest(String clientID) {
        this.type = MessageType.WAITING_LOBBY;
        this.clientID = clientID;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.getWaitingLobbies(clientID);
    }

}
