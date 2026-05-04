package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.GameStateInfo.GameStateInfo;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.view.View;

public class UpdateViewMessage extends Response {
    GameStateInfo gameStateInfo;

    public UpdateViewMessage(int clientID, GameStateInfo gameStateInfo){
        super(clientID);
        this.type = MessageType.UPDATE_VIEW_STATE;
        this.gameStateInfo = gameStateInfo;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.updateViewState(super.getClientID(), gameStateInfo);
    }

}
