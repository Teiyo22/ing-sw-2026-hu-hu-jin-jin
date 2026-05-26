package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.view.Formatter;

import java.util.Map;

public class TUILobbyInfoSection implements Section {
    private Lobby currLobby;

    @Override
    public void render(ClientController clientController) {
        System.out.println();
        System.out.println(Formatter.separatorLine("Lobby Info"));

        System.out.println(Formatter.lobbyID(currLobby.getLobbyID()));
        System.out.println(Formatter.playerCount(currLobby.getPlayerCount(), currLobby.getSize()));

        System.out.println(Formatter.line("Players:"));
        for (Map.Entry<Player, Boolean> entry : currLobby.getPlayers().entrySet())
            System.out.println(Formatter.player(entry));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        currLobby = clientController.getCurrLobby();
        return currLobby != null &&
               currLobby.getPlayers() != null &&
               !currLobby.getPlayers().isEmpty();
    }
}
