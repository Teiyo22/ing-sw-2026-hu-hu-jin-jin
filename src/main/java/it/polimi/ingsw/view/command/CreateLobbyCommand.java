package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Totem;

public class CreateLobbyCommand implements Command {
    private final ClientController clientController;
    private final int size;
    private final Totem totem;

    public CreateLobbyCommand(ClientController clientController, int size, Totem totem) {
        this.clientController = clientController;
        this.size = size;
        this.totem = totem;
    }

    @Override
    public void execute() {
        clientController.submitIOTask(
                () -> clientController.createLobby(size, totem)
        );
    }
}
