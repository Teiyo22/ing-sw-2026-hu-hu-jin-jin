package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

public class CreateLobbyRequest extends Request{
    private int playerNum;
    private Player player;

    public CreateLobbyRequest(int clientID, int playerNum, Player player) {
        super(clientID);
        this.type = MessageType.CREATE_LOBBY;
        this.playerNum = playerNum;
        this.player = player;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.createLobby(super.getClientID(), playerNum, player);
    }

}
