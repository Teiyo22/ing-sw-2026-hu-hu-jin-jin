package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.card.AbstractCard;

import java.util.List;

public class PickCardsRequest extends Request{
    private int lobbyID;
    private List<AbstractCard> topRowPicks;
    private List<AbstractCard> bottomRowPicks;

    public PickCardsRequest(int clientID, int lobbyID, List<AbstractCard> topRowPicks, List<AbstractCard> bottomRowPicks) {
        super(clientID);
        this.lobbyID = lobbyID;
        this.topRowPicks = topRowPicks;
        this.bottomRowPicks = bottomRowPicks;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.requestPick(super.getClientID(), lobbyID, topRowPicks, bottomRowPicks);
    }

}
