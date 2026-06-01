package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.view.Fonts;
import it.polimi.ingsw.utils.view.PanelBuilder;
import it.polimi.ingsw.utils.view.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankingSection extends GUISection {
    private static final String[] HEADERS = {"RANK", "PLAYER NAME", "PRESTIGE POINTS", "FOOD"};
    private final JPanel table;

    public RankingSection(ClientController clientController, JButton leaderboardBtn) {
        table = new PanelBuilder()
            .grid(HEADERS.length, 0, 2)
            .buildPanel();

        JLabel[] colHeaders = Arrays.stream(HEADERS)
            .map(h -> WidgetFactory.createRankCell(h, Fonts.mesos_shadow_red_low_opacity))
            .toArray(JLabel[]::new);

        JPanel header = new PanelBuilder()
            .column(5,
                WidgetFactory.mediumLabel("FINAL RANKING"),
                new PanelBuilder().grid(HEADERS.length, 0, 2, colHeaders).buildPanel())
            .buildPanel();

        panel = new PanelBuilder()
            .border(header, table, leaderboardBtn, null, null)
            .withTranslucentColor(Fonts.mesos_shadow_red_low_opacity)
            .withPadding(40, 60, 40, 60)
            .buildPanel();
    }

    @Override
    public void render(ClientController clientController) {
        Lobby currLobby = clientController.getCurrLobby();
        if (currLobby == null)
            return;

        table.removeAll();
        for (JLabel cell : buildTable(currLobby))
            table.add(cell);
    }

    private List<JLabel> buildTable(Lobby currLobby) {
        List<JLabel> cells = new ArrayList<>();


        List<Player> ranking = new ArrayList<>(currLobby.getPlayers().keySet());
        ranking.sort(null);

        for (Player player : ranking) {
            Color rowColor = rowColor(player.getRank());

            cells.add(WidgetFactory.createRankCell(String.valueOf(player.getRank()), rowColor));
            cells.add(WidgetFactory.createRankCell(player.getName(), rowColor));
            cells.add(WidgetFactory.createRankCell(String.valueOf(player.getPP()), rowColor));
            cells.add(WidgetFactory.createRankCell(String.valueOf(player.getFood()), rowColor));
        }

        return cells;
    }

    private static Color rowColor(int rank) {
        return switch (rank) {
            case 1 -> Fonts.gold;
            case 2 -> Fonts.silver;
            case 3 -> Fonts.bronze;

            default -> Fonts.mesos_shadow_red_low_opacity;
        };
    }
}
