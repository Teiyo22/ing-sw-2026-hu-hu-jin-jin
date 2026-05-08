package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.Screen;

public class JoinLobbyCommand implements Command {
    private final int lobbyID;
    private final Player player;

    public JoinLobbyCommand(int lobbyID, Player player) {
        this.lobbyID = lobbyID;
        this.player = player;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().joinLobby(clientController.getID(), lobbyID, player);
        });
    }
}
