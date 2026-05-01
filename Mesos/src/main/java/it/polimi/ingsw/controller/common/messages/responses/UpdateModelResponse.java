package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Tribe;


public class UpdateModelResponse extends Response {
    private Tribe updatedTribe;
    private Board updatedBoard;

    public UpdateModelResponse(int clientID, Board updatedBoard, Tribe updatedTribe){
        super(clientID);
        this.type = MessageType.UPDATE_MODEL;
        this.updatedBoard = updatedBoard;
        this.updatedTribe = updatedTribe;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.updateModel(super.getClientID(), updatedBoard, updatedTribe);
    }
}

