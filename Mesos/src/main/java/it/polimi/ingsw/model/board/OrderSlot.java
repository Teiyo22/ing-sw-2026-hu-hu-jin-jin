package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

public class OrderSlot implements Serializable {
    private Player assignedPlayer = null;
    private int foodDelta;

    public OrderSlot(int foodDelta) {
        this.foodDelta = foodDelta;
    }

    public OrderSlot(Player assignedPlayer, int foodDelta) {
        this.assignedPlayer = assignedPlayer;
        this.foodDelta = foodDelta;
    }

    public Player getAssignedPlayer() {
        return assignedPlayer;
    }

    public void setPlayer(Player assignedPlayer) {
        this.assignedPlayer = assignedPlayer;
    }

    public int getFoodDelta() {
        return foodDelta;
    }

    /**
     * Increases/decreases the food of the player assigned to this slot depending on the foodDelta.
     * */
    public void solveDeltaFood(){
        if(getAssignedPlayer().getFood() + foodDelta < 0)
            getAssignedPlayer().addPP(-2);
        else
            getAssignedPlayer().addFood(foodDelta);
    }

    public OrderSlot copy() {
        return new OrderSlot(assignedPlayer, foodDelta);
    }

    @Override
    public String toString() {
        String playerName = assignedPlayer == null ? "" : assignedPlayer.getName();
        return String.format("%-25s | %-15s", playerName, foodDelta);
    }
}
