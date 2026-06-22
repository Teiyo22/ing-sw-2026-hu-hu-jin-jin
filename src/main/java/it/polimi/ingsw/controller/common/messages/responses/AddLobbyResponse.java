package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.messages.Response;

public class AddLobbyResponse extends Response {
    private Lobby lobby;

    public AddLobbyResponse(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.addLobby(lobby);
    }
}
