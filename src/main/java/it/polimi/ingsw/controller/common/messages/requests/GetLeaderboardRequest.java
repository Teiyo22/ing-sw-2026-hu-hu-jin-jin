package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;

public class GetLeaderboardRequest extends Request {
    int playerNum;

    public GetLeaderboardRequest(String clientID, int playerNum) {
        this.clientID = clientID;
        this.playerNum = playerNum;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.getLeaderboard(clientID, playerNum);
    }

}
