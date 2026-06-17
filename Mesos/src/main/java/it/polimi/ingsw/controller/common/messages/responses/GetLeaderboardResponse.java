package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;
import it.polimi.ingsw.utils.leaderboard.LeaderboardResult;


public class GetLeaderboardResponse extends Response {
    private final LeaderboardResult leaderboardResult;

    public GetLeaderboardResponse(LeaderboardResult leaderboardResult) {
        this.type = MessageType.GET_LEADERBOARD;
        this.leaderboardResult = leaderboardResult;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showLeaderboard(leaderboardResult);
    }

}
