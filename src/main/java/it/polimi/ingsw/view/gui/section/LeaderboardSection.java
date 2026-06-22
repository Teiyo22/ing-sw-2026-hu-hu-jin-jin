package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.utils.leaderboard.LeaderboardEntry;
import it.polimi.ingsw.utils.leaderboard.LeaderboardResult;
import it.polimi.ingsw.utils.view.Fonts;
import it.polimi.ingsw.utils.view.PanelBuilder;
import it.polimi.ingsw.utils.view.WidgetFactory;
import it.polimi.ingsw.view.gui.action.GUILeaveLobbyAction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardSection extends GUISection {
    private static final String[] HEADERS = {"RANK", "NICKNAME", "PRESTIGE POINTS", "FOOD", "DATE"};
    private final JPanel table;

    public LeaderboardSection(ClientController clientController, JButton rankingBtn) {
        int playerCount = clientController.getCurrLobby().getPlayers().size();

        JLabel title = WidgetFactory.mediumLabel("LEADERBOARD — " + playerCount + " PLAYERS");

        table = new PanelBuilder()
            .grid(HEADERS.length, 0, 2)
            .buildPanel();

        JScrollPane scroll = WidgetFactory.scrollPane(table, null);

        JButton leaveBtn = WidgetFactory.mediumButton(new GUILeaveLobbyAction(clientController));
        JPanel btnPanel = new PanelBuilder()
            .row(5, rankingBtn, leaveBtn)
            .buildPanel();

        panel = new PanelBuilder()
            .column(5, title, scroll, btnPanel)
            .centered()
            .withPadding(40, 60, 40, 60)
            .buildPanel();
    }

    @Override
    public void render(ClientController clientController) {
        Lobby currLobby = clientController.getCurrLobby();
        if (currLobby == null)
            return;

        table.removeAll();
        for(JLabel cell : buildTable(currLobby.getLeaderboard()))
            table.add(cell);
    }

    private List<JLabel> buildTable(LeaderboardResult result) {
        List<JLabel> cells = new ArrayList<>();

        if (result != null) {
            for (String h : HEADERS)
                cells.add(WidgetFactory.createLeaderboardCell(h, Fonts.mesos_dark_blue_low_opacity));

            for (int i = 0; i < result.getLeaderboardEntries().size(); i++) {
                LeaderboardEntry entry = result.getLeaderboardEntries().get(i);
                int rank = i + 1;

                Color color = entry.getId() == result.getId()
                    ? new Color(255, 255, 255, 60)
                    : Fonts.mesos_shadow_red_low_opacity;

                cells.add(WidgetFactory.createLeaderboardCell(String.valueOf(rank), color));
                cells.add(WidgetFactory.createLeaderboardCell(entry.getNickname(), color));
                cells.add(WidgetFactory.createLeaderboardCell(String.valueOf(entry.getPP()), color));
                cells.add(WidgetFactory.createLeaderboardCell(String.valueOf(entry.getFood()), color));
                cells.add(WidgetFactory.createLeaderboardCell(entry.getDate().toString(), color));
            }
        }

        return cells;
    }
}
