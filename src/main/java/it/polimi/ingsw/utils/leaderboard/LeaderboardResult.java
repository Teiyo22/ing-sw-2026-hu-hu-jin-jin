package it.polimi.ingsw.utils.leaderboard;

import java.io.Serializable;
import java.util.List;

public class LeaderboardResult implements Serializable {
    private final List<LeaderboardEntry> leaderboardEntries;
    private final int id;

    public LeaderboardResult(List<LeaderboardEntry> leaderboardEntries, int id) {
        this.leaderboardEntries = leaderboardEntries;
        this.id = id;
    }

    public List<LeaderboardEntry> getLeaderboardEntries() {
        return leaderboardEntries;
    }

    public int getId() {
        return id;
    }
}
