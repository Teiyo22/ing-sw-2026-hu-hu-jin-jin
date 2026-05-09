package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.view.tui.Formatter;

import java.util.Map;

public class LobbyListSection implements Section {
    @Override
    public void render(ClientController clientController) {
        Map<Integer, Lobby> waitingLobbies = clientController.getWaitingLobbies();

        if (waitingLobbies == null || waitingLobbies.isEmpty())
            return;


        System.out.println();

        System.out.println(Formatter.separatorLine("Lobby List"));
        for (Lobby lobby : waitingLobbies.values())
            System.out.println(Formatter.waitingLobby(lobby));

        System.out.println(Formatter.separatorLine(""));
    }
}
