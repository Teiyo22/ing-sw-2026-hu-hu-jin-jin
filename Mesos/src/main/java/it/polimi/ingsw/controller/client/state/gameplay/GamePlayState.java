package it.polimi.ingsw.controller.client.state.gameplay;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.state.ClientState;
import it.polimi.ingsw.model.player.Player;

public class GamePlayState extends ClientState {
    Player currPlayer;

    public GamePlayState(ClientController clientController, Player currPlayer) {
        super(clientController);
        this.currPlayer = currPlayer;
    }

    @Override
    public void updateView() {

    }
}
