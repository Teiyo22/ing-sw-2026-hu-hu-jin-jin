package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

public class ExtraActionResponse extends Response {
    private int lobbyID;
    private Player player;
    private Row updateTopRow;

    public ExtraActionResponse(int lobbyID, Player player, Row updateTopRow) {
        this.type = MessageType.EXTRA_ACTION;
        this.lobbyID = lobbyID;
        this.player = player;
        this.updateTopRow = updateTopRow;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(lobbyID, player, updateTopRow);
    }
}
