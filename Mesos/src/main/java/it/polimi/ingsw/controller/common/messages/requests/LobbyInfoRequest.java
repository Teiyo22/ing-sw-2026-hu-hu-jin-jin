package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.server.ServerController;

public class LobbyInfoRequest extends Request{
    private int lobbyID;

    public LobbyInfoRequest(int clientID, int lobbyID) {
        super(clientID);
        this.type = MessageType.LOBBY_INFO;
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.getLobbyInfo(super.getClientID(), lobbyID);
    }

}
