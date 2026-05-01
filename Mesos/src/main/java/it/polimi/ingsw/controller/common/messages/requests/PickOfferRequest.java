package it.polimi.ingsw.controller.common.messages.requests;


import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class PickOfferRequest extends Request {
    private int lobbyID;
    private int offerIndex;

    public PickOfferRequest(int clientID, int lobbyID, int offerIndex) {
        super(clientID);
        this.type = MessageType.PICK_OFFER;
        this.lobbyID = lobbyID;
        this.offerIndex = offerIndex;
    }

    @Override
    public void receive(ServerController serverController) {
        serverController.requestOffer(clientID, lobbyID, offerIndex);
    }
}
