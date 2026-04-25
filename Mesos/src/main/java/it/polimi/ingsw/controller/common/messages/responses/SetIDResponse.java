package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

import java.util.Map;

public class SetIDResponse extends Response {
    private int ID;
    private int playerNum;
    private Map<Integer, String> players;
    private String playerName;
    private Totem totem;

    public SetIDResponse(int clientID, int lobbyID, int playerNum, Map<Integer, String> players, String playerName, Totem totem ){
        super(clientID);
        this.ID = lobbyID;
        this.playerNum = playerNum;
        this.players = players;
        this.playerName = playerName;
        this.totem = totem ;
    }

    @Override
    public void receive(ClientController clientController) {
        clientController.setLobby(super.getClientID(),ID,playerNum, players, playerName, totem );
    }
}
