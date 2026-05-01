package it.polimi.ingsw.controller.client.state.gameplay;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;

public class GameResultState extends GamePlayState {
    public GameResultState(ClientController clientController, Player currPlayer) {
        super(clientController, currPlayer);
    }

    @Override
    public void updateView() {

    }
}
