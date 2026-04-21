package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.model.card.AbstractCard;

import java.util.List;

public class PickCardsRequest extends Request{
    private int lobbyID;
    private List<AbstractCard> topRowPicks;
    private List<AbstractCard> bottomRowPicks;

    @Override
    public void receive(ServerController serverController){}

}
