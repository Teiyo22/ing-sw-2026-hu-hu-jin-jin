package it.polimi.ingsw.controller.common.messages.requests;

import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Request;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.network.TCPClientInterface;

public class GetLeaderboardRequest extends Request {
    int playerNum;

    public GetLeaderboardRequest(String clientID, int playerNum) {
        this.type = MessageType.GET_LEADERBOARD;
        this.clientID = clientID;
        this.playerNum = playerNum;
    }

    @Override
    public void receive(ServerController serverController){
        serverController.getLeaderboard(clientID, playerNum);
    }

}
