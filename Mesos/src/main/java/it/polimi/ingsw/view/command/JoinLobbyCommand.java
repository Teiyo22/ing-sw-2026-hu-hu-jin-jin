package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Totem;

public class JoinLobbyCommand implements Command {
    private final ClientController clientController;
    private final Totem totem;

    public JoinLobbyCommand(ClientController clientController, Totem totem) {
        this.clientController = clientController;
        this.totem = totem;
    }

    @Override
    public void execute() {
        clientController.joinLobby(totem);
    }
}
