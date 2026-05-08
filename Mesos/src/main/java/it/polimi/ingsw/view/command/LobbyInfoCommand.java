package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;

public class LobbyInfoCommand implements Command {
    private final int lobbyID;

    public LobbyInfoCommand(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().getLobbyInfo(clientController.getID(), lobbyID);
        });
    }
}
