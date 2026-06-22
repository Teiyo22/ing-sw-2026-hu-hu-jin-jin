package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Totem;

public class JoinLobbyRequest extends Request {
    private int lobbyID;
    private Totem totem;

    public JoinLobbyRequest(String clientID, int lobbyID, Totem totem) {
        this.clientID = clientID;
        this.lobbyID = lobbyID;
        this.totem = totem;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.joinLobby(clientID, lobbyID, totem);
    }

}
