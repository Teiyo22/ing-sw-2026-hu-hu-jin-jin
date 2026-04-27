package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Totem;

public class CreateLobbyRequest extends Request{
    private int playerNum;
    private String playerName;
    private Totem totem;

    public CreateLobbyRequest(int clientID, int playerNum, String playerName,  Totem totem) {
        super(clientID);
        this.type = MessageType.CREATE_LOBBY;
        this.playerNum = playerNum;
        this.playerName = playerName;
        this.totem = totem;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.createLobby(super.getClientID(), playerNum, playerName, totem);
    }

}
