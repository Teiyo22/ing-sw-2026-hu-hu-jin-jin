package it.polimi.ingsw.controller.client.state.gameplay;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;

public class CardPickState extends GamePlayState {
    private int topRowPicks;
    private int bottomRowPicks;

    public CardPickState(ClientController clientController, Player currPlayer, int topRowPicks, int bottomRowPicks) {
        super(clientController, currPlayer);
        this.topRowPicks = topRowPicks;
        this.bottomRowPicks = bottomRowPicks;
    }

    @Override
    public void updateView() {

    }
}
