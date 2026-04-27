package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Tribe;

import java.nio.file.attribute.AttributeView;
import java.util.List;


public class PickCardsResponse extends Response {
    private Tribe updatedTribe;
    private Row updatedTopRow;
    private Row updatedBottomRow;

    public PickCardsResponse(int clientID, Row updatedTopRow, Row updatedBottomRow, Tribe updatedTribe){
        super(clientID);
        this.updatedTopRow = updatedTopRow;
        this.updatedBottomRow = updatedBottomRow;
        this.updatedTribe = updatedTribe;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.confirmPick(super.getClientID(), updatedTopRow, updatedBottomRow, updatedTribe);
    }
}

