package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

public class OfferTile implements Serializable {
    private Player assignedPlayer = null;
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

    public String getType() {
        return type;
    }

    public OfferTile copy() {
        OfferTile copy = new OfferTile(bonusFood, topRowPickable, bottomRowPickable);
        copy.assignedPlayer = assignedPlayer != null ? assignedPlayer.shallowCopy() : null;
        copy.type = type;
        return copy;
    }

    @Override
    public String toString() {
        String playerName = assignedPlayer == null ? "" : assignedPlayer.getName();
        return String.format("%-25s | %-15s | %-15s | %-15s ", playerName, bonusFood, topRowPickable, bottomRowPickable);
    }
}
