package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Collection;
import java.util.Map;

public class GameEndResponse extends Response {
    private int lobbyID;
    private Collection<Player> players;

    public GameEndResponse(int lobbyID, Collection<Player> players) {
        this.type = MessageType.GAME_END;
        this.lobbyID = lobbyID;
        this.players = players;
    }

    @Override
    public void receive(ClientController clientController) {

    }
}
