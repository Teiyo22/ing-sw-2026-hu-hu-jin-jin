package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;

import java.util.Collection;
import java.util.List;

public class RoundEndResponse extends Response {
    private int lobbyID;
    private List<Player> players;
    private Row updatedTopRow;
    private Row updatedBottomRow;

    public RoundEndResponse(int lobbyID, List<Player> players, Row updatedTopRow, Row updatedBottomRow) {
        this.type = MessageType.ROUND_END;
        this.lobbyID = lobbyID;
        this.players = players;
        this.updatedTopRow = updatedTopRow;
        this.updatedBottomRow = updatedBottomRow;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(lobbyID, players, updatedTopRow, updatedBottomRow);
    }
}
