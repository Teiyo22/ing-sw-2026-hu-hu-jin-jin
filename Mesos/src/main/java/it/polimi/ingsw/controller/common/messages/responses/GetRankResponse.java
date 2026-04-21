package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

import java.util.HashMap;

public class GetRankResponse extends Response{
    private int lobbyID;
    private HashMap<String, Integer> ranking;

    @Override
    public void receive(ClientController clientController){

    }

}
