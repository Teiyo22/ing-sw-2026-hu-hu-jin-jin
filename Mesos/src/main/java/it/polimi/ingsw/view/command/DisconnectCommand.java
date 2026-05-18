package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class DisconnectCommand implements Command {
    private final ClientController clientController;

    public DisconnectCommand(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void execute() {
        clientController.submitRequest(
                clientController::disconnect
        );
    }
}
