package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import java.util.Map;

public class JoinLobbyResponse extends Response{
    private int lobbyID;
    private Player player;

    public JoinLobbyResponse(int clientID, int lobbyID , Player player) {
        super(clientID);
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.setLobby(super.getClientID(), lobbyID, player);
    }
}
