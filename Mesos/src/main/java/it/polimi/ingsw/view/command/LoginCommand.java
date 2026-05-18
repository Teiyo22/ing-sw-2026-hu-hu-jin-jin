package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class LoginCommand implements Command {
    final ClientController clientController;
    final String username;

    public LoginCommand(ClientController clientController, String username) {
        this.clientController = clientController;
        this.username = username;
    }

    @Override
    public void execute() {
        clientController.submitRequest(
                () -> clientController.login(username)
        );
    }
}
