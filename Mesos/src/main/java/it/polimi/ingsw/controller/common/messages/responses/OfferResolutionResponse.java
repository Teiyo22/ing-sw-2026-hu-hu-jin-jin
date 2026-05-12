package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;


public class OfferResolutionResponse extends Response {
    private int lobbyID;
    private Player player;
    private Tribe updatedTribe;
    private Board updatedBoard;

    public OfferResolutionResponse(int lobbyID, Player player, Tribe updatedTribe, Board updatedBoard){
        this.type = MessageType.OFFER_RESOLUTION;
        this.lobbyID = lobbyID;
        this.player = player;
        this.updatedBoard = updatedBoard;
        this.updatedTribe = updatedTribe;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.updateModel(lobbyID, player, updatedTribe, updatedBoard);
    }
}

