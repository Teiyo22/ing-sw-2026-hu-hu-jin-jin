package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

import java.util.Map;

public class GetRankResponse extends Response{
    private int lobbyID;
    private Map<Integer, Integer> ranking;

    public GetRankResponse(int clientID, int lobbyID, Map<Integer,Integer> ranking){
        super(clientID);
        this.lobbyID = lobbyID;
        this.ranking = ranking;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showRank(super.getClientID(), ranking);
    }

}
