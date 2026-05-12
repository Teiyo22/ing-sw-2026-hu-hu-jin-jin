package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

public class RemoveClientResponse extends Response {
    private int lobbyID;
    private Player player;

    public RemoveClientResponse(int lobbyID, Player player){
        this.type = MessageType.REMOVE_CLIENT;
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.removeClient(lobbyID, player);
    }
}
