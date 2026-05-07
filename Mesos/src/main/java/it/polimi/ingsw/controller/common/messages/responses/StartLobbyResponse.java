package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Map;

public class StartLobbyResponse extends Response {
    private int lobbyID;
    private Board board;
    private Map<Player, Tribe> tribes;

    public StartLobbyResponse(int clientID, int lobbyID, Board board, Map<Player, Tribe> tribes){
        super(clientID);
        this.type = MessageType.START_LOBBY;
        this.lobbyID = lobbyID;
        this.tribes = tribes;
        this.board = board;
    }
    @Override
    public void receive(ClientController clientController){
        clientController.startLobby(clientID, lobbyID, board, tribes);
    }
}

