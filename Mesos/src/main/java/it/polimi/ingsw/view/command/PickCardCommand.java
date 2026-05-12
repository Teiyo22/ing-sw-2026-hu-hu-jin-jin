package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

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
        clientController.executeCommand(() -> {
            clientController.getServer().requestCards(clientController.getID(),
                    clientController.getCurrLobby().getLobbyID(), topPicks, bottomPicks);
        });
    }
}
