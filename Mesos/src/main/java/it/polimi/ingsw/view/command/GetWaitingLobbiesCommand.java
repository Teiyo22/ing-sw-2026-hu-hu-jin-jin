package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;

public class GetWaitingLobbiesCommand implements Command {
    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().getWaitingLobbies(clientController.getID());
        });
    }
}
