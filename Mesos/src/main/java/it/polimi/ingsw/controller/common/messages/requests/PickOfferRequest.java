package it.polimi.ingsw.controller.common.messages.requests;


import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.network.TCPClientInterface;

public class PickOfferRequest extends Request {
    private int lobbyID;
    private int offerIndex;

    public PickOfferRequest(String clientID, int lobbyID, int offerIndex) {
        this.type = MessageType.PICK_OFFER;
        this.clientID = clientID;
        this.lobbyID = lobbyID;
        this.offerIndex = offerIndex;
    }

    @Override
    public void receive(ServerController serverController) {
        serverController.requestOffer(clientID, lobbyID, offerIndex);
    }
}
