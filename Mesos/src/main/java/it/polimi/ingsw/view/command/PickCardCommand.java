package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

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
            clientController.getServer().requestCards(clientController.getID(),
                    clientController.getCurrLobby().getLobbyID(), topPicks, bottomPicks);
        });
    }
}
