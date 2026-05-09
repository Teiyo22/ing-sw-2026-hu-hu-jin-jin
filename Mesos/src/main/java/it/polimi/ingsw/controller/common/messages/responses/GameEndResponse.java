package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Map;

public class GameEndResponse extends Response {
    private int lobbyID;
    private Map<Integer, Tribe> updatedTribes;
    private Map<Integer, Integer> ranking;

    public GameEndResponse(int clientID, int lobbyID, Map<Integer, Tribe> updatedTribes, Map<Integer, Integer> ranking) {
        super(clientID);
        this.type = MessageType.GAME_END;
        this.lobbyID = lobbyID;
        this.updatedTribes = updatedTribes;
        this.ranking = ranking;
    }

    @Override
    public void receive(ClientController clientController) {

    }
}
