package it.polimi.ingsw.controller.common.GameStateInfo;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.tui.TUIView;

public class OfferPickState extends GameStateInfo {
    public OfferPickState(Player currPlayer){
        super(currPlayer);
    }

    @Override
    public void setView(TUIView view) {
        if(currPlayer.getName().equals(view.getClientController().getPlayerName())){
            view.transitionTo(ScreenType.OFFER_PICK);
        }else
            view.transitionTo(ScreenType.GAME_PLAY);
    }
}
