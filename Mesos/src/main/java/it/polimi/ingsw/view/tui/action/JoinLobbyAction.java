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
    final private int argCount = 1;

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

        return currLobby != null &&
               !currLobby.containsClient(clientController.getID()) &&
               !currLobby.getPlayers().isEmpty();
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        String playerName;
        Totem totem;

        if (args.length != argCount + 1)
            return Optional.of("Invalid number of arguments");

        totem = parseTotem(args[1]);
        if (totem == null)
            return Optional.of("Totem must be unique and one of the following: RED, BLUE, WHITE, BLACK, YELLOW");

        new JoinLobbyCommand(currLobby.getLobbyID(), totem).execute(clientController);
        return Optional.empty();
    }

    private Totem parseTotem(String input) {
        try {
            Totem totem = Totem.valueOf(input.toUpperCase());

            for (Player player : currLobby.getPlayers().keySet())
                if ((player.getName().equals(clientController.getID()) && totem != player.getTotem()) ||
                    (!player.getName().equals(clientController.getID()) && totem == player.getTotem()))
                    return null;

            return totem;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private boolean validatePlayer(Player player) {
        for (Map.Entry<Player, Boolean> entry : currLobby.getPlayers().entrySet()) {
            if (entry.getKey().getName().equals(player.getName()) || entry.getKey().getTotem().equals(player.getTotem())) {
                return entry.getKey().equals(player) && entry.getValue() == false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <Totem>", key(), label());
    }
}
