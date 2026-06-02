package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.view.Formatter;

public class TUIPlayerInfoSection implements TUISection {
    private Lobby currLobby;

    @Override
    public void render(ClientController clientController) {
        Player currPlayer = currLobby.getCurrPlayer();
        Player thisPlayer = currLobby.getPlayer(clientController.getID());

        System.out.println();
        System.out.println(Formatter.separatorLine("Players"));

        for (Player player : currLobby.getPlayers().keySet())
            System.out.println(Formatter.playerInfo(player, player.equals(currPlayer), player.equals(thisPlayer)));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        currLobby = clientController.getCurrLobby();
        return currLobby != null && currLobby.getPlayers() != null;
    }
}
