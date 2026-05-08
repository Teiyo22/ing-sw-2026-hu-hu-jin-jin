package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public interface Command {
    void execute(ClientController clientController);
}
