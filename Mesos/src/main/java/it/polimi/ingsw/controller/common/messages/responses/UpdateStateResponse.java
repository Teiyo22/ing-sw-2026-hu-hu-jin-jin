package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

public class UpdateStateResponse extends Response {
    private int lobbyID;
    private ModelStateInfo modelStateInfo;

    public UpdateStateResponse(int lobbyID, ModelStateInfo modelStateInfo){
        this.type = MessageType.UPDATE_STATE;
        this.lobbyID = lobbyID;
        this.modelStateInfo = modelStateInfo;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.updateState(lobbyID, modelStateInfo);
    }

}
