package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Totem;

public class JoinLobbyRequest extends Request {
    private String lobbyID;
    private String playerName;
    private Totem totem;

    @Override
    public void receive(ServerController serverController){}

}
