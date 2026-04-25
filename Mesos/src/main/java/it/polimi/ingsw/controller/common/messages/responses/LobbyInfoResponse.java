package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;

import java.util.Map;

public class LobbyInfoResponse extends Response{
    private int lobbyID;
    private Map<Integer, Player> info ;

    public LobbyInfoResponse(int clientID ,int lobbyID, Map<Integer,Player> info){
        super(clientID);
        this.lobbyID = lobbyID;
        this.info = info;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showLobbyInfo(super.getClientID(), info);
    }
}
