package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;

public class GetRankRequest extends Request{
    private int lobbyID;

    public GetRankRequest(int clientID, int lobbyID) {
        super(clientID);
        this.lobbyID = lobbyID;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.getRank(super.getClientID(), lobbyID);
    }

}
