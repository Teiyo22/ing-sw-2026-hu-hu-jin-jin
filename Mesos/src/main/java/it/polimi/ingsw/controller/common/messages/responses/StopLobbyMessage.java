package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

public class StopLobbyMessage extends Response {
    int lobbyID;

    public StopLobbyMessage(int clientID, int lobbyID) {
        super(clientID);
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.stopLobby(lobbyID);
    }
}
