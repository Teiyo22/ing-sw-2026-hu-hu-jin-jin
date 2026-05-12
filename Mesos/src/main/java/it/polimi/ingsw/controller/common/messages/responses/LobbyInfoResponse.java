package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

import java.util.Set;

public class LobbyInfoResponse extends Response {
    private int lobbyID;
    private Set<Player> connectedPlayers;
    private Set<Player> disconnectedPlayers;

    public LobbyInfoResponse(int lobbyID, Set<Player> connectedPlayers, Set<Player> disconnectedPlayers){
        this.type = MessageType.LOBBY_INFO;
        this.lobbyID = lobbyID;
        this.connectedPlayers = connectedPlayers;
        this.disconnectedPlayers = disconnectedPlayers;
    }

    @Override
    public void receive(ClientController clientController){
            clientController.showLobbyInfo(lobbyID, connectedPlayers, disconnectedPlayers);
    }
}
