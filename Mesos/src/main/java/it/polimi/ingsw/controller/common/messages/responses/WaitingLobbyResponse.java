package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

import java.util.List;

public class WaitingLobbyResponse extends Response {
    private List<Lobby> lobbies;

    public WaitingLobbyResponse(int clientID, List<Lobby> lobbies){
        super(clientID);
        this.type = MessageType.WAITING_LOBBY;
        this.lobbies = lobbies;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.showWaitingLobbies(super.getClientID(), lobbies);
    }
}
