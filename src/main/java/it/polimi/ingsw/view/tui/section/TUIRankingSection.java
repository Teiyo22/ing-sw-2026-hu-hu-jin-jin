package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.view.Formatter;

import java.util.ArrayList;
import java.util.List;

public class TUIRankingSection implements TUISection {
    private Lobby currLobby;

    @Override
    public void render(ClientController clientController) {
        List<Player> players = new ArrayList<>(currLobby.getPlayers().keySet());
        players.sort(null);

        System.out.println();
        System.out.println(Formatter.separatorLine("Ranking"));
        for (Player player : players)
            System.out.println(Formatter.playerRank(player));
        System.out.println(Formatter.separatorLine(""));

    }

    @Override
    public boolean isVisible(ClientController clientController) {
        currLobby = clientController.getCurrLobby();
        return currLobby != null;
    }
}
