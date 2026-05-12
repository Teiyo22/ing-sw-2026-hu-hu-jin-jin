package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.network.TCPClientInterface;

import java.util.Set;

public class PickCardsRequest extends Request {
    private int lobbyID;
    private Set<Integer> topRowPicks;
    private Set<Integer> bottomRowPicks;

    public PickCardsRequest(String clientID, int lobbyID, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        this.type = MessageType.PICK_CARDS;
        this.clientID = clientID;
        this.lobbyID = lobbyID;
        this.topRowPicks = topRowPicks;
        this.bottomRowPicks = bottomRowPicks;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.requestCards(clientID, lobbyID, topRowPicks, bottomRowPicks);
    }

}
