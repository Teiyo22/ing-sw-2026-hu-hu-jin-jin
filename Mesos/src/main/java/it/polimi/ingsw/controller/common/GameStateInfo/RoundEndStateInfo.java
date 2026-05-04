package it.polimi.ingsw.controller.common.GameStateInfo;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.tui.TUIView;

public class RoundEndStateInfo extends GameStateInfo{

    public RoundEndStateInfo(Player player){
        super(player);
    }

    @Override
    public void setView(TUIView view){
        view.transitionTo(ScreenType.ROUND_END);
    }
}
