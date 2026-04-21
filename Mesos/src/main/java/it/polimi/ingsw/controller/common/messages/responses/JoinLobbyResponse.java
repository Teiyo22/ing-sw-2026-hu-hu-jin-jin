package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Totem;

public class JoinLobbyResponse extends Response{
    private int lobbyID;
    private String playerName;
    private Totem totem;

    @Override
    public void receive(ClientController clientController){

    }
}
