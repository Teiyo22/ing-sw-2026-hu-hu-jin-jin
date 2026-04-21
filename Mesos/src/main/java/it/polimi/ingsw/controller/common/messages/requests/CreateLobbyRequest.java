package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.player.Totem;

public class CreateLobbyRequest extends Request{
    private String playerName;
    private Totem totem;

    @Override
    public void receive(ServerController serverController){}

}
