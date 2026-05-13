package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class LoginCommand implements Command {
    final String username;

    public LoginCommand(String username) {
        this.username = username;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.getServer().login(clientController.getID(), username);
    }
}
