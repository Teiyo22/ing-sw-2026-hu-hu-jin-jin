package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.utils.LeaderboardEntry;
import it.polimi.ingsw.utils.LeaderboardResult;
import it.polimi.ingsw.utils.view.Formatter;

public class TUILeaderboardSection implements TUISection {
    private Lobby currLobby;

    @Override
    public void render(ClientController clientController) {
        LeaderboardResult leaderboard = currLobby.getLeaderboard();

        System.out.println();
        System.out.println(Formatter.separatorLine(String.format("Leaderboard (%s Players)", currLobby.getSize())));

        int rank = 1;
        for (LeaderboardEntry entry : leaderboard.getLeaderboardEntries()) {
            System.out.println(Formatter.leaderboardEntry(rank, entry, leaderboard.getId()));
            rank++;
        }

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
         currLobby = clientController.getCurrLobby();
         return currLobby != null && currLobby.getLeaderboard() != null;
    }
}
