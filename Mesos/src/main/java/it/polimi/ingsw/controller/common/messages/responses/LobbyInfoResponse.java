package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;

import java.util.Map;

public class LobbyInfoResponse extends Response{
    private Lobby lobby;

    public LobbyInfoResponse(int clientID ,Lobby lobby){
        super(clientID);
        this.lobby = lobby;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showLobbyInfo(super.getClientID(), lobby);
    }
}
