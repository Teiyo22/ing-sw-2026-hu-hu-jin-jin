package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.player.Player;

public interface Pickable {
    public abstract void onPick(Player player);
}
