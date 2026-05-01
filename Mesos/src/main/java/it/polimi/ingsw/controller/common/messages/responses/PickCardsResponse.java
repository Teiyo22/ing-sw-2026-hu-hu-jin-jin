package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Tribe;


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

