package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

public class OfferTile {
    private transient Player assignedPlayer = null;
    private int bonusFood;
    private int topRowPickable;
    private int bottomRowPickable;

    public OfferTile(int bonusFood, int topRowPickable, int bottomRowPickable) {
        this.bonusFood = bonusFood;
        this.topRowPickable = topRowPickable;
        this.bottomRowPickable = bottomRowPickable;
    }

    public Player getAssignedPlayer() {
        return assignedPlayer;
    }

    public void setPlayer(Player assignedPlayer) {
        this.assignedPlayer = assignedPlayer;
    }

    public void solveBonusFood() {
        assignedPlayer.addFood(bonusFood);
    }

}
