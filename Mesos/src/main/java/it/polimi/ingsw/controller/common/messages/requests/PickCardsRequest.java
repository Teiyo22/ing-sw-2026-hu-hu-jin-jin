package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.server.ServerController;

public class PickCardsRequest extends Request{
    private String lobbyID;
    private List<Card> topRowPicks;
    private List<Card> bottomRowPicks;

    @Override
    public void receive(ServerController serverController){}

}
