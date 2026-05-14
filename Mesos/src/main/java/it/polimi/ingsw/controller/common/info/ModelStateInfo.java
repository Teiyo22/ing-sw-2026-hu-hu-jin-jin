package it.polimi.ingsw.controller.common.info;

import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;


public abstract class ModelStateInfo implements Serializable {
    protected StateInfoType type;
    protected Player currPlayer;
    protected int idx;
    protected int era;

    public ModelStateInfo(Player player, int idx, int era){
        this.currPlayer = player;
        this.idx = idx;
        this.era = era;
    }

    public abstract TurnState getTurnState (Player player);

    public StateInfoType getType(){
        return type;
    }
}
