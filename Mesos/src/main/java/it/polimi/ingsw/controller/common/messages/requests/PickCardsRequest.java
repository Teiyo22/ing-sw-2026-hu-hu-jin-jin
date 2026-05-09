package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

import java.util.Set;

public class PickCardsRequest extends Request {
    private int lobbyID;
    private Set<Integer> topRowPicks;
    private Set<Integer> bottomRowPicks;

    public PickCardsRequest(int clientID, int lobbyID, Set<Integer> topRowPicks, Set<Integer> bottomRowPicks) {
        super(clientID);
        this.type = MessageType.PICK_CARDS;
        this.lobbyID = lobbyID;
        this.topRowPicks = topRowPicks;
        this.bottomRowPicks = bottomRowPicks;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.requestCards(super.getClientID(), lobbyID, topRowPicks, bottomRowPicks);
    }

}
