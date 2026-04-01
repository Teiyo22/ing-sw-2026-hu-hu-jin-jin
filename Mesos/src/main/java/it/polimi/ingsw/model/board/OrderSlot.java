package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

public class OrderSlot {
    private Player assignedPlayer = null;
    private final int foodDelta;

    public OrderSlot(int foodDelta) {
        this.foodDelta = foodDelta;
    }

    public Player getAssignedPlayer() {
        return assignedPlayer;
    }

    public void setAssignedPlayer(Player assignedPlayer) {
        this.assignedPlayer = assignedPlayer;
    }

    public int getFoodDelta() {
        return foodDelta;
    }
}
