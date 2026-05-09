package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.JoinLobbyCommand;

import java.util.Map;
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

        totem = parseTotem(args[2]);
        if (totem == null)
            return Optional.of("Totem must be unique and one of the following: RED, BLUE, WHITE, BLACK, YELLOW");


        Player player = new Player(playerName, totem);

        if (!validatePlayer(player))
            return Optional.of("Invalid name and/or totem");

        new JoinLobbyCommand(currLobby.getLobbyID(), player).execute(clientController);
        return Optional.empty();
    }

    private String parseName(String input) {
        return input;
    }

    private Totem parseTotem(String input) {
        try {
            return Totem.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private boolean validatePlayer(Player player) {
        for (Map.Entry<Player, Integer> entry : currLobby.getPlayers().entrySet()) {
            if (entry.getKey().getName().equals(player.getName()) || entry.getKey().getTotem().equals(player.getTotem())) {
                return entry.getKey().equals(player) && entry.getValue() == null;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <Player Name> <Totem>", key(), label());
    }
}
