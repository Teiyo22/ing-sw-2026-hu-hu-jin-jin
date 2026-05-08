package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class StopLobbyResponse extends Response {
    private int lobbyID;

    public StopLobbyResponse(int clientID, int lobbyID){
        super(clientID);
        this.type = MessageType.STOP_LOBBY;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.stopLobby(clientID, lobbyID);
    }
}
