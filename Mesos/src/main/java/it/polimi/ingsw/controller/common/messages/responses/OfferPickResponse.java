package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;

public class OfferPickResponse extends Response {
    private int lobbyID;
    private OrderSlot[] orderTile;
    private OfferTile[] offerTrack;

    public OfferPickResponse(int clientID, int lobbyID, OrderSlot[] orderTile, OfferTile[] offerTrack) {
        super(clientID);
        this.type = MessageType.OFFER_PICK;
        this.lobbyID = lobbyID;
        this.orderTile = orderTile;
        this.offerTrack = offerTrack;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(clientID, lobbyID, orderTile, offerTrack);
    }
}
