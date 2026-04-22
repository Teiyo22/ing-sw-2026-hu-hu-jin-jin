package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Totem;

public class JoinLobbyRequest extends Request {
    private int lobbyID;
    private String playerName;
    private Totem totem;

    public JoinLobbyRequest(int clientID, int lobbyID, String playerName, Totem totem) {
        super(clientID);
        this.lobbyID = lobbyID;
        this.playerName = playerName;
        this.totem = totem;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.joinLobby(super.getClientID(), lobbyID, playerName, totem);
    }

}
