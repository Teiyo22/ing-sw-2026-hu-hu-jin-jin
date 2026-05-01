package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

import java.util.Map;

public class GetRankResponse extends Response {
    private int lobbyID;
    private Map<Integer, Integer> rankings;

    public GetRankResponse(int clientID, int lobbyID,  Map<Integer,Integer> rankings){
        super(clientID);
        this.type = MessageType.GET_RANK;
        this.rankings = rankings;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showRank(super.getClientID(), lobbyID, rankings);
    }

}
