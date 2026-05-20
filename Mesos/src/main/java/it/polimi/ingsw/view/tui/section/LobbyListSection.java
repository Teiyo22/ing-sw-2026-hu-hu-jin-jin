package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.tui.Formatter;

import java.util.Map;

public class LobbyListSection implements Section {
    private Map<Integer, Lobby> waitingLobbies;

    @Override
    public void render(ClientController clientController) {
        System.out.println();
        System.out.println(Formatter.separatorLine("Lobby List"));
        for (Lobby lobby : waitingLobbies.values())
            System.out.println(Formatter.waitingLobby(lobby));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        waitingLobbies = clientController.getWaitingLobbies();

        return !waitingLobbies.isEmpty();
    }
}
