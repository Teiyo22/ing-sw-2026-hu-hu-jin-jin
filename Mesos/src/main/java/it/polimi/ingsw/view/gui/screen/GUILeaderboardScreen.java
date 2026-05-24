package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.utils.LeaderboardEntry;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GUILeaderboardScreen extends GUIScreen{
    private static final String[] HEADERS = { "RANK", "NICKNAME", "PRESTIGE POINTS", "FOOD", "DATE"};

    public GUILeaderboardScreen(GUIView frame, ClientController clientController) {
        super(frame, clientController);

        int playerCount = clientController.getCurrLobby().getPlayers().size();

        Map<String, LeaderboardEntry> bestScore = new LinkedHashMap<>();
        for (LeaderboardEntry e : clientController.getLeaderboard()) {
            bestScore.putIfAbsent(e.getNickname(), e);
        }
        List<LeaderboardEntry> deduplicated = new ArrayList<>(bestScore.values());

        JLabel title = WidgetFactory.createLabel("LEADERBOARD — " + playerCount + " PLAYERS");
        JPanel table  = buildTable(deduplicated, clientController.getID());

        JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel panel = new PanelBuilder()
                .border(title, scroll, null, null, null)
                .withPadding(40, 60, 40, 60)
                .buildPanel();
        panel.setOpaque(false);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setOpaque(false);
    }

    private JPanel buildTable(List<LeaderboardEntry> entries, String myName) {
        List<JComponent> cells = new ArrayList<>();

        for (String h : HEADERS)
            cells.add(WidgetFactory.createLeaderboardCell(h, Fonts.mesos_dark_blue_low_opacity));

        for (int i = 0; i < entries.size(); i++) {
            LeaderboardEntry player = entries.get(i);
            int rank = i + 1;

            Color color = player.getNickname().equalsIgnoreCase(myName) ? new Color(255, 255, 255, 60) : Fonts.mesos_shadow_red_low_opacity;

            cells.add(WidgetFactory.createLeaderboardCell(String.valueOf(rank), color));
            cells.add(WidgetFactory.createLeaderboardCell(player.getNickname(), color));
            cells.add(WidgetFactory.createLeaderboardCell(String.valueOf(player.getPP()), color));
            cells.add(WidgetFactory.createLeaderboardCell(String.valueOf(player.getFood()), color));
            cells.add(WidgetFactory.createLeaderboardCell(player.getDate(), color));
        }

        JPanel grid = new PanelBuilder().grid(5, 0, 2, cells.toArray(new JComponent[0])).buildPanel();
        grid.setOpaque(false);
        return grid;
    }

    @Override
    public void render() {
        frame.setContentPane(this);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }


    @Override
    public void showError(String error) {
    }

}
