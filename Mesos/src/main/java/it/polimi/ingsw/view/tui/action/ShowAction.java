package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;

import java.util.Optional;

public class ShowAction implements Action {
    final private ClientController clientController;
    final private int argCount = 1;

    public ShowAction(ClientController clientController) {
        this.clientController = clientController;
    }



    @Override
    public String key() {
        return "1";
    }

    @Override
    public String label() {
        return "Show";
    }

    @Override
    public boolean isEnabled() {
        Lobby currLobby = clientController.getCurrLobby();
        return currLobby != null && !currLobby.isShownPlayer();
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        Player player;

        if (args.length != argCount + 1)
            return Optional.of("Invalid number of arguments");

        player = parseName(args[1]);
        if (player == null)
            return Optional.of("Invalid player name");

        clientController.showPlayer(player);
        return Optional.empty();
    }

    private Player parseName(String input) {
        Lobby currLobby = clientController.getCurrLobby();

        for (Player player : currLobby.getPlayers().keySet())
            if (player.getName().equals(input))
                return player;

        return null;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <Player Name>", key(), label());
    }
}
