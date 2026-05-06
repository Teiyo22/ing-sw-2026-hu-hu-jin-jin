package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class LobbyInfoCommand implements Command {
    private final ClientController clientController;
    private final int lobbyID;

    public LobbyInfoCommand(ClientController clientController, int lobbyID) {
        this.clientController = clientController;
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute() {
        clientController.executeCommand(() -> {
            clientController.getServer().getLobbyInfo(clientController.getID(), lobbyID);
        });
    }
}
