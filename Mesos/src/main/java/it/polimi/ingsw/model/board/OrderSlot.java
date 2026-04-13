package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;
import com.google.gson.annotations.Expose;

public class OrderSlot {
    private transient Player assignedPlayer = null; //transient is used to let Gson ignore this parameter
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

    public void solveDeltaFood(){
        assignedPlayer.addFood(foodDelta);
    }
}
