package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.network.TCPClientInterface;
import it.polimi.ingsw.model.player.Player;

public class CreateLobbyRequest extends Request {
    private int playerNum;
    private Player player;

    public CreateLobbyRequest(String clientID, int playerNum, Player player) {
        this.type = MessageType.CREATE_LOBBY;
        this.clientID = clientID;
        this.playerNum = playerNum;
        this.player = player;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.createLobby(clientID, playerNum, player);
    }

}
