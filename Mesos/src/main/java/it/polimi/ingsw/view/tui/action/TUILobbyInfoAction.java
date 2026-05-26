package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.command.LobbyInfoCommand;

import java.util.Map;
import java.util.Optional;

public class TUILobbyInfoAction implements Action {
    final private ClientController clientController;
    final private int argCount = 1;

    public TUILobbyInfoAction(ClientController clientController) {
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
        Map<Integer, Lobby> waitingLobbies = clientController.getWaitingLobbies();
        Lobby currLobby = clientController.getCurrLobby();

        return !waitingLobbies.isEmpty() &&
               !(currLobby != null && currLobby.containsClient(clientController.getID()));
    }

    @Override
    public boolean parseAction(String[] args) {
        Integer lobbyID;

        if (args.length != argCount + 1)
            return false;

        lobbyID = parseLobbyID(args[1]);
        if (lobbyID == null)
            return false;

        new LobbyInfoCommand(clientController, lobbyID).execute();
        return true;

    }

    private Integer parseLobbyID(String input) {
        Map<Integer, Lobby> waitingLobbies = clientController.getWaitingLobbies();
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
