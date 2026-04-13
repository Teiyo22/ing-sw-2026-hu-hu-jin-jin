package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

public class OfferTile {
    private Player assignedPlayer = null;
    private final int bonusFood;
    private final int topRowPickable;
    private final int bottomRowPickable;

    public OfferTile(int bonusFood, int topRowPickable, int bottomRowPickable) {
        this.bonusFood = bonusFood;
        this.topRowPickable = topRowPickable;
        this.bottomRowPickable = bottomRowPickable;
    }

    public Player getAssignedPlayer() {
        return assignedPlayer;
    }

    public void setAssignedPlayer(Player assignedPlayer) {
        this.assignedPlayer = assignedPlayer;
    }

    public void solveBonusFood() {
        public Player p = getAssignedPlayer();
        p.addFood(bonusFood);
    }
}
