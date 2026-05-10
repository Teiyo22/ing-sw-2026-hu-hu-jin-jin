package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.tui.Formatter;

public class PlayerInfoSection implements  Section {
    @Override
    public void render(ClientController clientController) {
        Player currPlayer = clientController.getCurrLobby().getCurrPlayer();
        Player thisPlayer = clientController.getCurrLobby().getPlayer(clientController.getID());

        System.out.println();
        System.out.println(Formatter.separatorLine("Players"));

        for (Player player : clientController.getCurrLobby().getPlayers().keySet())
            System.out.println(Formatter.playerInfo(player, player.equals(currPlayer), player.equals(thisPlayer)));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        Lobby currLobby = clientController.getCurrLobby();

        return currLobby != null &&
               currLobby.getTurnState() != null &&
               !currLobby.isShownPlayer();
    }
}
