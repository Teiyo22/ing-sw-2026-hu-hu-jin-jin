package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;

public class LeaveLobbyCommand implements Command {
    private final int lobbyID;

    public LeaveLobbyCommand(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().leaveLobby(clientController.getID(), lobbyID);
        });
    }
}
