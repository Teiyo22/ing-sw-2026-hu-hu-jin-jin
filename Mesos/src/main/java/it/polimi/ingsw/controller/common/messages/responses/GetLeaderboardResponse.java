package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.LeaderboardEntry;

import java.util.List;

public class GetLeaderboardResponse extends Response{
    private List<LeaderboardEntry> leaderboards;

    public GetLeaderboardResponse(int clientId ,List<LeaderboardEntry> leaderboards){
        super(clientId);
        this.leaderboards = leaderboards;
    }

    @Override
    public void receive(ClientController clientController){
        clientController.showLeaderboard(super.getClientID(), leaderboards);
    }

}
