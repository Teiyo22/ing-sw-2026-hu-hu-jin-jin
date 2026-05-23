package it.polimi.ingsw.utils;

public class LeaderboardEntry {
    private final String nickname;
    private final int pp;
    private final int food;
    private final String date;

    public LeaderboardEntry(String nickname, int pp, int food, String date) {
        this.nickname = nickname;
        this.pp = pp;
        this.food = food;
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPP() {
        return pp;
    }

    public int getFood() {
        return food;
    }

    public String getDate() {
        return date;
    }
}
