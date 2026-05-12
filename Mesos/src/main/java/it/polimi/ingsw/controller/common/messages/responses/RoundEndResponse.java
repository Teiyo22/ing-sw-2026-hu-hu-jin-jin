package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Map;

public class RoundEndResponse extends Response {
    private int lobbyID;
    private Map<String, Tribe> updatedTribes;
    private Row updatedTopRow;
    private Row updatedBottomRow;

    public RoundEndResponse(int lobbyID, Map<String, Tribe> updatedTribes, Row updatedTopRow, Row updatedBottomRow) {
        this.type = MessageType.ROUND_END;
        this.lobbyID = lobbyID;
        this.updatedTribes = updatedTribes;
        this.updatedTopRow = updatedTopRow;
        this.updatedBottomRow = updatedBottomRow;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(lobbyID, updatedTribes, updatedTopRow, updatedBottomRow);
    }
}
