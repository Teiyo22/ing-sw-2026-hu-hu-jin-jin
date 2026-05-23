package it.polimi.ingsw.utils;

import java.util.List;

public class LeaderboardResult {
    private final List<LeaderboardEntry> leaderboardEntries;
    private final int playerRank;

    public LeaderboardResult(List<LeaderboardEntry> leaderboardEntries, int playerRank) {
        this.leaderboardEntries = leaderboardEntries;
        this.playerRank = playerRank;
    }

    public List<LeaderboardEntry> getLeaderboardEntries() {
        return leaderboardEntries;
    }

    public int getPlayerRank() {
        return playerRank;
    }
}
