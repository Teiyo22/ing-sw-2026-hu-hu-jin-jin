package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Totem;

public class JoinLobbyResponse extends Response{
    private int lobbyID;
    private String playerName;
    private Totem totem;

    public JoinLobbyResponse(int clientID, int lobbyID,String playerName, Totem totem){
        super(clientID);
        this.lobbyID = lobbyID;
        this.playerName = playerName;
        this.totem = totem;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.setLobby(super.getClientID(), lobbyID, playerName,totem);
    }
}
