package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class LeaderboardCommand implements Command{
    private final ClientController clientController;

    public LeaderboardCommand(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void execute() {
        clientController.getLeaderboard();
    }
}
