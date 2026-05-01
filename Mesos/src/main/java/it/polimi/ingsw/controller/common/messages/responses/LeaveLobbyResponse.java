package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class LeaveLobbyResponse extends Response {
    private int lobbyID;

    public LeaveLobbyResponse(int clientID, int lobbyID){
        super(clientID);
        this.type = MessageType.LEAVE_LOBBY;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.removeFromLobby(super.getClientID(), lobbyID);
    }
}
