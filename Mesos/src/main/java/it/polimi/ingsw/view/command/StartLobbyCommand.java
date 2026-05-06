package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.server.ServerController;

public class StartLobbyCommand implements Command {
    private ClientController clientController;
    private int lobbyID;

    public StartLobbyCommand(ClientController clientController, int lobbyID) {
        this.clientController = clientController;
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute() {
        clientController.executeCommand(() -> {
            clientController.getServer().startLobby(clientController.getID(), lobbyID);
        });
    }
}
