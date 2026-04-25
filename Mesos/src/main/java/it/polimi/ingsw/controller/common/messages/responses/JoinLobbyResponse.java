package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Totem;

import java.util.Map;

public class JoinLobbyResponse extends Response{
    private int lobbyID;
    private int playerNum;
    private Map<Integer, String> players;
    private String playerName;
    private Totem totem;

    public JoinLobbyResponse(int clientID, int lobbyID, int playerNum ,Map<Integer,String> players, String playerName, Totem totem) {
        super(clientID);
        this.lobbyID = lobbyID;
        this.playerNum = playerNum;
        this.players = players;
        this.playerName = playerName;
        this.totem = totem;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.setLobby(super.getClientID(), lobbyID, playerName,totem);
    }
}
