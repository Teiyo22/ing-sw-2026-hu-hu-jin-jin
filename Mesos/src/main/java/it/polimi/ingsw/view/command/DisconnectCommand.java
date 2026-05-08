package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;

public class DisconnectCommand implements Command {
    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(clientController::disconnect);

    }
}
