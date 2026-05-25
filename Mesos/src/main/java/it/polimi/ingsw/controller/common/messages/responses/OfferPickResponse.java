package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.player.Player;

public class OfferPickResponse extends Response {
    private int lobbyID;
    private Player player;
    private int offerIndex;

    public OfferPickResponse(int lobbyID, Player player, int offerIndex) {
        this.type = MessageType.OFFER_PICK;
        this.lobbyID = lobbyID;
        this.player = player;
        this.offerIndex = offerIndex;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(lobbyID, player, offerIndex);
    }
}
