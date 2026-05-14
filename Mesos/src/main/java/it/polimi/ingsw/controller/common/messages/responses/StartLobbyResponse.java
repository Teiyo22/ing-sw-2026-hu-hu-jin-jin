package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tribe;

import java.util.Collection;
import java.util.List;

public class StartLobbyResponse extends Response {
    private int lobbyID;
    private Board board;
    private List<Player> players;

    public StartLobbyResponse(int lobbyID, Board board, List<Player> players){
        this.type = MessageType.START_LOBBY;
        this.lobbyID = lobbyID;
        this.board = board;
        this.players = players;
    }
    @Override
    public void receive(ClientController clientController){
        clientController.startLobby(lobbyID, board, players);
    }
}

