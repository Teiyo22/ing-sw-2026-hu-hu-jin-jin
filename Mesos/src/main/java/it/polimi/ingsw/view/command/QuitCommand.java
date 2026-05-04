package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class QuitCommand implements Command {
    private final ClientController clientController;

    public QuitCommand(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void execute() {
        clientController.disconnect();
    }
}
