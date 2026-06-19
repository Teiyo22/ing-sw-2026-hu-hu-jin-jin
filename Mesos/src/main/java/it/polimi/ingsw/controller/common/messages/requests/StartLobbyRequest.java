package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class StartLobbyRequest extends Request {
    private int lobbyID;

    public StartLobbyRequest(String clientID, int lobbyID) {
        this.clientID = clientID;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.startLobby(clientID, lobbyID);
    }

}
