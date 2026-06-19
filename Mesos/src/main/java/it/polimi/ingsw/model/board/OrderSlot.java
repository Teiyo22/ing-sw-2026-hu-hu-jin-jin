package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

public class OrderSlot implements Serializable {
    private Player assignedPlayer = null;
    private int foodDelta;

    public OrderSlot(int foodDelta) {
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
        if(assignedPlayer.getFood() + foodDelta < 0)
            assignedPlayer.addPP(-2);
        else
            assignedPlayer.addFood(foodDelta);
    }

    public OrderSlot copy() {
        OrderSlot copy = new OrderSlot(foodDelta);
        copy.assignedPlayer = assignedPlayer != null ? assignedPlayer.shallowCopy() : null;
        return copy;
    }

    @Override
    public String toString() {
        String playerName = assignedPlayer == null ? "" : assignedPlayer.getName();
        return String.format("%-25s | %-15s", playerName, foodDelta);
    }
}
