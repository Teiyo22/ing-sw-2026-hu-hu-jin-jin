package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;

public class JoinLobbyResponse extends Response {
    private int lobbyID;
    private Player player;

    public JoinLobbyResponse(int clientID, int lobbyID , Player player) {
        super(clientID);
        this.type = MessageType.JOIN_LOBBY;
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.setLobby(super.getClientID(), lobbyID, player);
    }
}
