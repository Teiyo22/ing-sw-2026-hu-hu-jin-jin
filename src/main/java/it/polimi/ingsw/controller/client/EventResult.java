package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

public class EventResult implements Serializable {
    private Player player;
    private int ppDelta;
    private int foodDelta;
    
    public EventResult(Player player) {
        this.player = player;
        this.ppDelta = player.getPP();
        this.foodDelta = player.getFood();
    }

    public int getFoodDelta() {
        return foodDelta;
    }

    public int getPPDelta() {
        return ppDelta;
    }

    public Player getPlayer() {
        return player;
    }

    public void computeDelta() {
        ppDelta = player.getPP() - ppDelta;
        foodDelta = player.getFood() - foodDelta;
        player = player.shallowCopy();
    }

    @Override
    public String toString() {
        return String.format("%10s | %10d | %10d", player.getName(), foodDelta, ppDelta);
    }
}
