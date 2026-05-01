package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class GetLeaderboardRequest extends Request {
    int playerNum;

    public GetLeaderboardRequest(int clientID, int playerNum) {
        super(clientID);
        this.type = MessageType.GET_LEADERBOARD;
        this.playerNum = playerNum;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.getLeaderboard(super.getClientID(), playerNum);
    }

}
