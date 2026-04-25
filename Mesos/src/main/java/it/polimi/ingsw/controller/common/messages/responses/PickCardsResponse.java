package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Tribe;

import java.nio.file.attribute.AttributeView;
import java.util.List;


public class PickCardsResponse extends Response {
    private int lobbyID;
    private Tribe updatedTribe;
    private List<AbstractCard> updatedTopRowPick;
    private List<AbstractCard> updatedBottomRowPick;

    public PickCardsResponse(int clientID, int lobbyID, List<AbstractCard> topRowPick, List<AbstractCard> bottomRowPick){
        super(clientID);
        this.lobbyID = lobbyID;
        this.updatedTopRowPick = topRowPick;
        this.updatedBottomRowPick = bottomRowPick;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.confirmPick(super.getClientID(), updatedTopRowPick, updatedBottomRowPick);
    }
}

