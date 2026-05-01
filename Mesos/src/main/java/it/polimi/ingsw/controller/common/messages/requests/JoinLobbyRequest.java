package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Player;

public class JoinLobbyRequest extends Request {
    private int lobbyID;
    private Player player;

    public JoinLobbyRequest(int clientID, int lobbyID, Player player) {
        super(clientID);
        this.type = MessageType.JOIN_LOBBY;
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.joinLobby(super.getClientID(), lobbyID, player);
    }

}
