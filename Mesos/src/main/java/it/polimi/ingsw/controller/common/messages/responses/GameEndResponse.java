package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GameEndResponse extends Response {
    private int lobbyID;
    private List<Player> players;

    public GameEndResponse(int lobbyID, List<Player> players) {
        this.type = MessageType.GAME_END;
        this.lobbyID = lobbyID;
        this.players = players;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(lobbyID, players);
    }
}
