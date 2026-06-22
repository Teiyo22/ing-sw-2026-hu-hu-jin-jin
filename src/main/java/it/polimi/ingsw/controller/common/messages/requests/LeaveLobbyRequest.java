package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class LeaveLobbyRequest extends Request {
    private int lobbyID;

    public LeaveLobbyRequest(String clientID, int lobbyID) {
        this.clientID = clientID;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.leaveLobby(clientID, lobbyID);
    }

}
