package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.Screen;

public class CreateLobbyCommand implements Command {
    private final int size;
    private final Player player;

    public CreateLobbyCommand(int size, Player player) {
        this.size = size;
        this.player = player;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().createLobby(clientController.getID(), size, player);
        });
    }
}
