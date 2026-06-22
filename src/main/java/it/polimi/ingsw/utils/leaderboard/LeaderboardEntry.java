package it.polimi.ingsw.utils.leaderboard;

import java.io.Serializable;
import java.util.Date;

public class LeaderboardEntry implements Serializable {
    private int id;
    private String nickname;
    private int pp;
    private int food;
    private Date date;

    public LeaderboardEntry(int id, String nickname, int pp, int food, Date date) {
        this.id = id;
        this.nickname = nickname;
        this.pp = pp;
        this.food = food;
        this.date = date;
    }

    public int getId() {
        return id;
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

    public Date getDate() {
        return date;
    }
}
