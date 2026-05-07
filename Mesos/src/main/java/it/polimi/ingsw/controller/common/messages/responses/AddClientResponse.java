package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

public class AddClientResponse extends Response {
    private int lobbyID;
    private Player player;

    public AddClientResponse(int clientID, int lobbyID , Player player) {
        super(clientID);
        this.type = MessageType.ADD_CLIENT;
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.addClient(super.getClientID(), lobbyID, player);
    }
}
