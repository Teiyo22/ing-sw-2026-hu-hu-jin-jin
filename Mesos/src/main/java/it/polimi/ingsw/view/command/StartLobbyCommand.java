package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class StartLobbyCommand implements Command {
    private final ClientController clientController;

    public StartLobbyCommand(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void execute() {
        clientController.submitIOTask(
                clientController::startLobby
        );
    }
}
