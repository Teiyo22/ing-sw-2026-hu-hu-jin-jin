package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.card.Pickable;

import java.util.List;

public class PickCardsRequest extends Request{
    private int lobbyID;
    private List<Pickable> topRowPicks;
    private List<Pickable> bottomRowPicks;

    public PickCardsRequest(int clientID, int lobbyID, List<Pickable> topRowPicks, List<Pickable> bottomRowPicks) {
        super(clientID);
        this.type = MessageType.PICK_CARDS;
        this.lobbyID = lobbyID;
        this.topRowPicks = topRowPicks;
        this.bottomRowPicks = bottomRowPicks;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.requestPick(super.getClientID(), lobbyID, topRowPicks, bottomRowPicks);
    }

}
