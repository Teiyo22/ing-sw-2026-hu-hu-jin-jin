package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.client.action.PlayerAction;

public class PlayerActionRequest extends Request {
    private int lobbyID;
    private PlayerAction playerAction;

    public PlayerActionRequest(String clientID, int lobbyID, PlayerAction playerAction) {
        this.type = MessageType.PLAYER_ACTION;
        this.clientID = clientID;
        this.lobbyID = lobbyID;
        this.playerAction = playerAction;
    }

    @Override
    public void receive(ServerController serverController) {
        serverController.requestAction(clientID, lobbyID, playerAction);
    }
}
