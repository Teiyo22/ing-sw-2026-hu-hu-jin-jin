package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

public class AddPlayerResponse extends Response {
    private int lobbyID;
    private Player player;

    public AddPlayerResponse(int lobbyID , Player player) {
        this.type = MessageType.ADD_PLAYER;
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.addPlayer(lobbyID, player);
    }
}
