package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Response;

public class StopLobbyResponse extends Response {
    private int lobbyID;

    public StopLobbyResponse(int lobbyID){
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.stopLobby(lobbyID);
    }
}
