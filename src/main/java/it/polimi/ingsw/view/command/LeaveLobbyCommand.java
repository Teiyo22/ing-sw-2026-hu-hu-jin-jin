package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class LeaveLobbyCommand implements Command {
    private final ClientController clientController;

    public LeaveLobbyCommand(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void execute() {
        clientController.submitIOTask(
                clientController::leaveLobby
        );
    }
}
