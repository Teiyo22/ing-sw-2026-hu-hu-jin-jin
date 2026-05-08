package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

import java.util.List;

public class PickCardCommand implements Command {
    private List<Integer> topPicks;
    private List<Integer> bottomPicks;

    public PickCardCommand(List<Integer> topPicks, List<Integer> bottomPicks) {
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
