package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Totem;

public class JoinLobbyCommand implements Command {
    private final ClientController clientController;
    private final int lobbyID;
    private final Totem totem;

    public JoinLobbyCommand(ClientController clientController, int lobbyID, Totem totem) {
        this.clientController = clientController;
        this.lobbyID = lobbyID;
        this.totem = totem;
    }

    @Override
    public void execute() {
        clientController.getServer().joinLobby(clientController.getID(), lobbyID, totem);
    }
}
