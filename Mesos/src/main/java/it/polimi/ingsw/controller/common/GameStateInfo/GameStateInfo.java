package it.polimi.ingsw.controller.common.GameStateInfo;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.tui.TUIView;


public abstract class GameStateInfo {
    Player currPlayer;

    public GameStateInfo(Player player){
        this.currPlayer = player;
    }

    public abstract void setView (TUIView view);
}
