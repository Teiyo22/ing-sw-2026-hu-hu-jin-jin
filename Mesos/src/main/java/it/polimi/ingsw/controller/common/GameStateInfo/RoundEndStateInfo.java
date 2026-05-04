package it.polimi.ingsw.controller.common.GameStateInfo;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;

public class RoundEndStateInfo extends GameStateInfo{

    public RoundEndStateInfo(Player player){
        super(player);
    }

    @Override
    public void setView(View view){
        view.transitionTo(ScreenType.ROUND_END);
    }
}
