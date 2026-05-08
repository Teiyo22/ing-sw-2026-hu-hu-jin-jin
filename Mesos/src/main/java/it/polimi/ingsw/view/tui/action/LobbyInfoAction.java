package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.view.command.LobbyInfoCommand;

import java.util.Map;
import java.util.Optional;

public class LobbyInfoAction implements Action {
    final private ClientController clientController;
    final private int argCount = 1;

    Map<Integer, Lobby> waitingLobbies;
    Lobby currLobby;

    public LobbyInfoAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "3";
    }

    @Override
    public String label() {
        return "Info";
    }

    @Override
    public boolean isEnabled() {
        waitingLobbies = clientController.getWaitingLobbies();
        currLobby = clientController.getCurrLobby();

        return waitingLobbies != null &&
               !waitingLobbies.isEmpty() &&
               !(currLobby != null && currLobby.containsClient(clientController.getID()));
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        Integer lobbyID;

        if (args.length != argCount + 1)
            return Optional.of("Invalid number of arguments");

        lobbyID = parseLobbyID(args[1]);
        if (lobbyID == null)
            return Optional.of("Lobby ID must be an integer from the list of available lobbies");

        new LobbyInfoCommand(lobbyID).execute(clientController);
        return Optional.empty();

    }

    private Integer parseLobbyID(String input) {
        try {
            Integer n = Integer.parseInt(input);

            if (!waitingLobbies.containsKey(n))
                return null;

            return n;

        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <Lobby ID>", key(), label());
    }
}
