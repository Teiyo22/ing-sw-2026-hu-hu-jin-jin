package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

public class RemoveClientResponse extends Response {
    private int lobbyID;
    private Player player;

    public RemoveClientResponse(int clientID, int lobbyID, Player player){
        super(clientID);
        this.type = MessageType.REMOVE_CLIENT;
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.removeClient(super.getClientID(), lobbyID, player);
    }
}
