package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.action.CardPickPlayerAction;
import it.polimi.ingsw.model.action.OfferPickPlayerAction;

import java.util.Set;

public class PickCardCommand implements Command {
    private Set<Integer> topPicks;
    private Set<Integer> bottomPicks;

    public PickCardCommand(Set<Integer> topPicks, Set<Integer> bottomPicks) {
        this.topPicks = topPicks;
        this.bottomPicks = bottomPicks;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().requestAction(clientController.getID(),
                    clientController.getCurrLobby().getLobbyID(), new CardPickPlayerAction(topPicks, bottomPicks));
        });
    }
}
