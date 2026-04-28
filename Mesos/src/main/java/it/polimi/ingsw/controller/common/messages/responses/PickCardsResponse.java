package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.character.*;
import it.polimi.ingsw.model.player.Tribe;

import java.nio.file.attribute.AttributeView;
import java.util.List;


public class PickCardsResponse extends Response {
    private Tribe updatedTribe;
    private Board updatedBoard;

    public PickCardsResponse(int clientID, Board updatedBoard, Tribe updatedTribe){
        super(clientID);
        this.type = MessageType.PICK_CARDS;
        this.updatedBoard = updatedBoard;
        this.updatedTribe = updatedTribe;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.confirmPick(super.getClientID(), updatedBoard, updatedTribe);
    }
}

