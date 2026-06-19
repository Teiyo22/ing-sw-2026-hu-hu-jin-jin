package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

public class RemovePlayerResponse extends Response {
    private int lobbyID;
    private Player player;

    public RemovePlayerResponse(int lobbyID, Player player){
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.removePlayer(lobbyID, player);
    }
}
