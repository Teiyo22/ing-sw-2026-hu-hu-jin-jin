package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;

public class LeaveLobbyRequest extends Request{
    private int lobbyID;

    public LeaveLobbyRequest(int clientID, int lobbyID) {
        super(clientID);
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.leaveLobby(super.getClientID(), lobbyID);
    }

}
