package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.JoinLobbyCommand;

import java.util.Optional;

public class JoinLobbyAction implements Action {
    final private ClientController clientController;
    final private int argCount = 2;

    private Lobby currLobby;

    public JoinLobbyAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "4";
    }

    @Override
    public String label() {
        return "Join";
    }

    @Override
    public boolean isEnabled() {
        currLobby = clientController.getCurrLobby();

        return currLobby != null && !currLobby.containsClient(clientController.getID());
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        String playerName;
        Totem totem;

        if (args.length != argCount + 1)
            return Optional.of("Invalid number of arguments");

        playerName = parseName(args[1]);
        if (playerName == null)
            return Optional.of("Player name must be unique");

        totem = parseTotem(args[2]);
        if (totem == null)
            return Optional.of("Totem must be unique and one of the following: RED, BLUE, WHITE, BLACK, YELLOW");

        new JoinLobbyCommand(currLobby.getLobbyID(), new Player(playerName, totem)).execute(clientController);
        return Optional.empty();
    }

    private String parseName(String input) {
        return validateName(input) ? input : null;
    }

    private Totem parseTotem(String input) {
        try {
            Totem result = Totem.valueOf(input.toUpperCase());

            return validateTotem(result)
                    ? result
                    : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private boolean validateName(String name) {
        for (Player player : currLobby.getPlayers().keySet())
            if (player.getName().equals(name))
                return false;

        return true;
    }

    private boolean validateTotem(Totem totem) {
        for (Player player : currLobby.getPlayers().keySet())
            if (player.getTotem().equals(totem))
                return false;

        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <Player Name> <Totem>", key(), label());
    }
}
