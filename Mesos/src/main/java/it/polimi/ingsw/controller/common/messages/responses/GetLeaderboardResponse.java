package it.polimi.ingsw.controller.common.messages.responses;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.LeaderboardEntry;

import java.util.List;

public class GetLeaderboardResponse extends Response{
    private List<LeaderboardEntry> leaderboards;

    @Override
    public void receive(ClientController clientController){

    }

}
