package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.CreateLobbyCommand;

import java.util.Map;
import java.util.Optional;

public class CreateLobbyAction implements Action {
    final private ClientController clientController;
    final private int argCount = 2;

    public CreateLobbyAction(ClientController clientController) {
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
    public Optional<String> parseAction(String[] args) {
        Integer lobbySize;
        Totem totem;

        if (args.length != argCount + 1)
            return Optional.of("Invalid number of arguments");

        lobbySize = parseSize(args[1]);
        if (lobbySize == null)
            return Optional.of("Lobby size must be an integer between 2 and 5");

        totem = parseTotem(args[2]);
        if (totem == null)
            return Optional.of("Totem must be one of the following: RED, BLUE, WHITE, BLACK, YELLOW");

        new CreateLobbyCommand(clientController, lobbySize, totem).execute();
        return Optional.empty();
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
