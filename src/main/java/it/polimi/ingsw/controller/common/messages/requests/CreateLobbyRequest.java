package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Totem;

public class CreateLobbyRequest extends Request {
    private int playerNum;
    private Totem totem;

    public CreateLobbyRequest(String clientID, int playerNum, Totem totem) {
        this.clientID = clientID;
        this.playerNum = playerNum;
        this.totem = totem;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.createLobby(clientID, playerNum, totem);
    }

}
