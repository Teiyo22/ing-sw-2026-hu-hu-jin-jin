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
    private Tribe updatedTribe;
    private Row updateTopRow;

    public ExtraActionResponse(int clientID, int lobbyID, Player player, Tribe updatedTribe, Row updateTopRow) {
        super(clientID);
        this.type = MessageType.EXTRA_ACTION;
        this.lobbyID = lobbyID;
        this.player = player;
        this.updatedTribe = updatedTribe;
        this.updateTopRow = updateTopRow;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(clientID, lobbyID, player, updatedTribe, updateTopRow);
    }
}
