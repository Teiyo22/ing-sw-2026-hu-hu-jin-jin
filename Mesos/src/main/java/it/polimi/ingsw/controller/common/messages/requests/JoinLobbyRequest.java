package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.network.TCPClientInterface;
import it.polimi.ingsw.model.player.Player;

public class JoinLobbyRequest extends Request {
    private int lobbyID;
    private Player player;

    public JoinLobbyRequest(String clientID, int lobbyID, Player player) {
        this.type = MessageType.JOIN_LOBBY;
        this.clientID = clientID;
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.joinLobby(clientID, lobbyID, player);
    }

}
