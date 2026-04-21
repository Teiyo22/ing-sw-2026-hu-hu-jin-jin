package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;

import java.util.HashMap;

public class WaitingLobbyResponse extends Response{
    private HashMap<String,Integer> lobbies;

    @Override
    public void receive(ClientController clientController) {

    }
}
