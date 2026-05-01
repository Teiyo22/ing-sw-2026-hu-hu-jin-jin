package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class RemoveLobbyMessage extends Response {
    int lobbyID;

    public RemoveLobbyMessage(int clientID, int lobbyID) {
        super(clientID);
        this.type = MessageType.REMOVE_LOBBY;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.removeLobby(lobbyID);
    }
}
