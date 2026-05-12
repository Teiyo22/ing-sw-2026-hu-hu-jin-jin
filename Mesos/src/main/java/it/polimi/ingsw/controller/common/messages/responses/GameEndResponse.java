package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Map;

public class GameEndResponse extends Response {
    private int lobbyID;
    private Map<String, Tribe> updatedTribes;
    private Map<String, Integer> ranking;

    public GameEndResponse(int lobbyID, Map<String, Tribe> updatedTribes, Map<String, Integer> ranking) {
        this.type = MessageType.GAME_END;
        this.lobbyID = lobbyID;
        this.updatedTribes = updatedTribes;
        this.ranking = ranking;
    }

    @Override
    public void receive(ClientController clientController) {

    }
}
