package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

public class OfferTile {
    private transient Player assignedPlayer = null;
    private String type;
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

    public int getTopRowPickable() {
        return topRowPickable;
    }

    public int  getBottomRowPickable() {
        return bottomRowPickable;
    }

    public int getBonusFood() {
        return bonusFood;
    }
}
