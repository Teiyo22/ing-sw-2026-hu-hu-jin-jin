package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.Screen;

public class JoinLobbyCommand implements Command {
    private final int lobbyID;
    private final Totem totem;

    public JoinLobbyCommand(int lobbyID, Totem totem) {
        this.lobbyID = lobbyID;
        this.totem = totem;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().joinLobby(clientController.getID(), lobbyID, totem);
        });
    }
}
