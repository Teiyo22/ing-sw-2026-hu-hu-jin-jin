package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.character.*;

import java.util.List;


public class PickCardsResponse extends Response {
    private int lobbyID;
    private List<AbstractCard> topRowPick;
    private List<AbstractCard> bottomRowPick;

    @Override
    public void receive(ClientController clientController){

    }
}

