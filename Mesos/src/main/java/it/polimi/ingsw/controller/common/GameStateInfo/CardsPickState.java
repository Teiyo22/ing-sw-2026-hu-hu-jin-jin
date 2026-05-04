package it.polimi.ingsw.controller.common.GameStateInfo;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;

public class CardsPickState extends GameStateInfo {

    public CardsPickState(Player currPlayer){
        super(currPlayer);
    }

    @Override
    public void setView(View view) {
        if(currPlayer.getName().equals(view.getClientController().getPlayerName())){
            view.transitionTo(ScreenType.CARD_PICK);
        }else
            view.transitionTo(ScreenType.GAME_PLAY);
    }
}
