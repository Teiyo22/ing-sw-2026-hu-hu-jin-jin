package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;

public class StartLobbyCommand implements Command {
    private int lobbyID;

    public StartLobbyCommand(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().startLobby(clientController.getID(), lobbyID);
        });
    }
}
