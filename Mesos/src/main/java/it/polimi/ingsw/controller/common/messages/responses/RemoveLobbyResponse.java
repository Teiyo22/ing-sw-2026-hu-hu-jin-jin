package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class RemoveLobbyResponse extends Response {
    private int lobbyID;

    public RemoveLobbyResponse(int lobbyID) {
        this.type = MessageType.REMOVE_LOBBY;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.removeLobby(lobbyID);
    }
}
