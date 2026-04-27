package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

public class LeaveLobbyResponse extends Response{
    private int lobbyID;

    public LeaveLobbyResponse(int clientID, int lobbyID){
        super(clientID);
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.removeFromLobby(super.getClientID(), lobbyID);
    }
}
