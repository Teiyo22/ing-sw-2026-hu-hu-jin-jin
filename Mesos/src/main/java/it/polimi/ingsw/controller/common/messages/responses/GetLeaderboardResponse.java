package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.LeaderboardEntry;
import it.polimi.ingsw.controller.common.messages.MessageType;
import it.polimi.ingsw.controller.common.messages.Response;

import java.util.List;

public class GetLeaderboardResponse extends Response {
    private List<LeaderboardEntry> leaderboards;

    public GetLeaderboardResponse(List<LeaderboardEntry> leaderboards){
        this.type = MessageType.GET_LEADERBOARD;
        this.leaderboards = leaderboards;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showLeaderboard(leaderboards);
    }

}
