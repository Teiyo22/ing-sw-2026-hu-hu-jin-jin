package it.polimi.ingsw.controller.client.state.gameplay;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;

public class OfferPickState extends GamePlayState {
    public OfferPickState(ClientController clientController, Player currPlayer) {
        super(clientController, currPlayer);
    }

    @Override
    public void updateView() {

    }
}
