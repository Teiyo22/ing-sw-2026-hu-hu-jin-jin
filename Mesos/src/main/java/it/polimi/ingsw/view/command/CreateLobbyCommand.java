package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.Screen;

public class CreateLobbyCommand implements Command {
    private final int size;
    private final Totem totem;

    public CreateLobbyCommand(int size, Totem totem) {
        this.size = size;
        this.totem = totem;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().createLobby(clientController.getID(), size, totem);
        });
    }
}
