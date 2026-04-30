package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

public class DeleteLobbyMessage extends Response {
    int lobbyID;

    public DeleteLobbyMessage(int clientID, int lobbyID) {
        super(clientID);
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.deleteLobby(lobbyID);
    }
}
