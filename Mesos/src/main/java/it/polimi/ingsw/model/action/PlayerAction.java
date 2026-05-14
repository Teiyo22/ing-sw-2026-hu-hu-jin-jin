package it.polimi.ingsw.model.action;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

public abstract class PlayerAction implements Serializable {
    protected ActionType type;
    transient protected Player player;

    public abstract void execute(Game game);
    public abstract String canExecute(Game game);

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ActionType getType() {
        return type;
    }
}
