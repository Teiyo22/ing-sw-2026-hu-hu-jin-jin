package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class UpdateStateResponse extends Response {
    int lobbyID;
    ModelStateInfo modelStateInfo;

    public UpdateStateResponse(int clientID, int lobbyID, ModelStateInfo modelStateInfo){
        super(clientID);
        this.type = MessageType.UPDATE_STATE;
        this.lobbyID = lobbyID;
        this.modelStateInfo = modelStateInfo;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.updateState(super.getClientID(), lobbyID, modelStateInfo);
    }

}
