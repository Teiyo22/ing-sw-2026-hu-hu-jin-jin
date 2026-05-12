package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

public class CreateLobbyResponse extends Response {
    private Lobby lobby;
    private Player player;

    public CreateLobbyResponse(Lobby lobby, Player player) {
        this.type = MessageType.CREATE_LOBBY;
        this.lobby = lobby;
        this.player = player;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.createLobby(lobby, player);
    }
}
