package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class LeaveLobbyCommand implements Command {
    private final ClientController clientController;
    private final int lobbyID;

    public LeaveLobbyCommand(ClientController clientController, int lobbyID) {
        this.clientController = clientController;
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute() {
        clientController.getServer().leaveLobby(clientController.getID(), lobbyID);
        clientController.setCurrLobby(null);
    }
}
