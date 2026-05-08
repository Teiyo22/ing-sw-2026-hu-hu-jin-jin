package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.tui.Formatter;

import java.util.Map;

public class LobbyInfoSection implements Section {
    @Override
    public void render(ClientController clientController) {
        Lobby currLobby = clientController.getCurrLobby();

        if (currLobby == null)
            return;

        System.out.println(Formatter.separatorLine("Lobby Info"));

        System.out.println(Formatter.lobbyID(currLobby.getLobbyID()));
        System.out.println(Formatter.playerCount(currLobby.getPlayerCount(), currLobby.getSize()));

        System.out.println(Formatter.line("Players:"));
        for (Map.Entry<Player, Integer> entry : currLobby.getPlayers().entrySet())
            System.out.println(Formatter.player(entry));
    }
}
