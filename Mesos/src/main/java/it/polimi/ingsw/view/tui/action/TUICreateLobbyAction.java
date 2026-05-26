package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.CreateLobbyCommand;

import java.util.Optional;

public class TUICreateLobbyAction implements Action {
    final private ClientController clientController;
    final private int argCount = 2;

    public TUICreateLobbyAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "2";
    }

    @Override
    public String label() {
        return "Create";
    }

    @Override
    public boolean isEnabled() {
        Lobby currLobby = clientController.getCurrLobby();

        return currLobby == null || !currLobby.containsClient(clientController.getID());
    }

    @Override
    public boolean parseAction(String[] args) {
        Integer lobbySize;
        Totem totem;

        if (args.length != argCount + 1)
            return false;

        lobbySize = parseSize(args[1]);
        totem = parseTotem(args[2]);
        if (lobbySize == null || totem == null)
            return false;

        new CreateLobbyCommand(clientController, lobbySize, totem).execute();
        return true;
    }

    private Integer parseSize(String input) {
        try {
            int n = Integer.parseInt(input);

            return (n >= 2 && n <= 5)
                    ? n
                    : null;

        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Totem parseTotem(String input) {
        try {
            return Totem.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <Lobby Size> <Totem>", key(), label());
    }
}
