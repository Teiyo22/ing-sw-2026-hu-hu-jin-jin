package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.LoginCommand;

import java.util.Optional;

public class TUILoginAction implements Action{
    private final ClientController clientController;
    private final int argsCount = 1;


    public TUILoginAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "1";
    }

    @Override
    public String label() {
        return "Login";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean parseAction(String[] args) {
        String username;

        if (args.length != argsCount + 1)
            return false;

        username = args[1];

        new LoginCommand(clientController, username).execute();
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <username>", key(), label());
    }
}
