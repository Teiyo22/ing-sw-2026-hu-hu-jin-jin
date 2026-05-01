package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

import java.util.Map;

public class LobbyInfoResponse extends Response {
    private int lobbyID;
    private Map<Integer, Player> players;

    public LobbyInfoResponse(int clientID, int lobbyID, Map<Integer, Player> players){
        super(clientID);
        this.type = MessageType.LOBBY_INFO;
        this.lobbyID = lobbyID;
        this.players = players;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showLobbyInfo(super.getClientID(), lobbyID, players);
    }
}
