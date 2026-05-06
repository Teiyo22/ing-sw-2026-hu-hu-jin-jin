package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

public class CreateLobbyCommand implements Command {
    private final ClientController clientController;

    private final int size;
    private final Player player;

    public CreateLobbyCommand(ClientController clientController, int size, Player player) {
        this.clientController = clientController;
        this.size = size;
        this.player = player;
    }

    @Override
    public void execute() {
        clientController.executeCommand(() -> {
            clientController.getServer().createLobby(clientController.getID(), size, player);
        });
    }
}
