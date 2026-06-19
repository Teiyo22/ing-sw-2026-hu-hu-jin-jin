package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.messages.Response;

public class UpdateLobbyResponse extends Response {
    private Lobby lobby;

    public UpdateLobbyResponse(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateLobby(lobby);
    }
}
