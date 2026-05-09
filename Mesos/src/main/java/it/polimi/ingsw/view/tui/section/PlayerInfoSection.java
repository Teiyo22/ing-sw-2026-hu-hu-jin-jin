package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.tui.Formatter;

public class PlayerInfoSection implements  Section {
    @Override
    public void render(ClientController clientController) {
        Player currPlayer = clientController.getCurrLobby().getCurrPlayer();

        System.out.println(Formatter.separatorLine("Players"));

        for (Player player : clientController.getCurrLobby().getPlayers().keySet())
            System.out.println(Formatter.playerInfo(player, player.equals(currPlayer)));
    }
}
