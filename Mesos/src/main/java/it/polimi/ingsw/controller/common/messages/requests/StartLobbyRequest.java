package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class StartLobbyRequest extends Request {
    private int lobbyID;

    public StartLobbyRequest(int clientID, int lobbyID) {
        super(clientID);
        this.type = MessageType.START_LOBBY;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.startLobby(super.getClientID(), lobbyID);
    }

}
