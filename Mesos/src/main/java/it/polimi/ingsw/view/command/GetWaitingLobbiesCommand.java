package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class GetWaitingLobbiesCommand implements Command {
    private final ClientController clientController;

    public GetWaitingLobbiesCommand(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void execute() {
        clientController.executeCommand(() -> {
            clientController.getServer().getWaitingLobbies(clientController.getID());
        });
    }
}
