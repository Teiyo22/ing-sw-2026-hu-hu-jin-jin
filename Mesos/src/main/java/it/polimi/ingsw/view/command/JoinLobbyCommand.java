package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;

public class JoinLobbyCommand implements Command {
    private final ClientController clientController;
    private final int lobbyID;
    private final Player player;

    public JoinLobbyCommand(ClientController clientController, Player player) {
        this.clientController = clientController;
        this.lobbyID = clientController.getCurrLobby().getLobbyID();
        this.player = player;
    }

    @Override
    public void execute() {
        clientController.executeCommand(() -> {
            clientController.getServer().joinLobby(clientController.getID(), lobbyID, player);
        });
    }
}
