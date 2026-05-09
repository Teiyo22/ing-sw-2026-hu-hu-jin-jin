package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Map;

public class RoundEndResponse extends Response {
    private int lobbyID;
    private Map<Integer, Tribe> updatedTribes;
    private Row updatedTopRow;
    private Row updatedBottomRow;

    public RoundEndResponse(int clientID, int lobbyID, Map<Integer, Tribe> updatedTribes, Row updatedTopRow, Row updatedBottomRow) {
        super(clientID);
        this.type = MessageType.ROUND_END;
        this.lobbyID = lobbyID;
        this.updatedTribes = updatedTribes;
        this.updatedTopRow = updatedTopRow;
        this.updatedBottomRow = updatedBottomRow;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(clientID, lobbyID, updatedTribes, updatedTopRow, updatedBottomRow);
    }
}
