package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.action.CardPickPlayerAction;

import java.util.Set;

public class PickCardCommand implements Command {
    private final ClientController clientController;
    private Set<Integer> topPicks;
    private Set<Integer> bottomPicks;

    public PickCardCommand(ClientController clientController, Set<Integer> topPicks, Set<Integer> bottomPicks) {
        this.clientController = clientController;
        this.topPicks = topPicks;
        this.bottomPicks = bottomPicks;
    }

    @Override
    public void execute() {
        clientController.requestAction(new CardPickPlayerAction(topPicks, bottomPicks));
    }
}
