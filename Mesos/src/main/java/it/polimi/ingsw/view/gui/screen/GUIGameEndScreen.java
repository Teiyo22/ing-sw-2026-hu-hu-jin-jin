package it.polimi.ingsw.view.gui.screen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.view.gui.action.GUILeaderboardAction;
import it.polimi.ingsw.view.gui.action.GUILoginAction;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

public class GUIGameEndScreen extends GUIScreen {
    private static final int COLS = 4;
    private JPanel panel;

    private static final String[] HEADERS = { "RANK", "PLAYER NAME", "PRESTIGE POINTS", "FOOD" };

    public GUIGameEndScreen(GUIView frame, ClientController clientController) {
        super(frame, clientController);


        JButton leaderboard = WidgetFactory.createButton(new GUILeaderboardAction(clientController));
        leaderboard.setBorderPainted(true);
        leaderboard.setText("See Leaderboard");

        panel = new PanelBuilder()
                .border(WidgetFactory.createLabel("FINAL RANKING"), buildTable(), leaderboard, null, null)
                .withPadding(40, 60, 40, 60)
                .buildPanel();

        panel.setOpaque(false);

        panel.setBackground(Fonts.mesos_shadow_red_low_opacity);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setOpaque(false);
    }

    @Override
    public void render() {
        frame.setContentPane(this);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private JPanel buildTable() {
        List<JComponent> cells = new ArrayList<>();

        for (String header : HEADERS){
            cells.add(WidgetFactory.createRankCell(header, Fonts.mesos_dark_blue_low_opacity));
        }

        List<Player> ranking = new ArrayList<>(clientController.getCurrLobby().getPlayers().keySet());
        ranking.sort(null);


        for (Player player : ranking) {
            Color rowColor = rowColor(player.getRank());

            cells.add(WidgetFactory.createRankCell(String.valueOf(player.getRank()), rowColor));
            cells.add(WidgetFactory.createRankCell(player.getName(), rowColor));
            cells.add(WidgetFactory.createRankCell(String.valueOf(player.getPP()), rowColor));
            cells.add(WidgetFactory.createRankCell(String.valueOf(player.getFood()), rowColor));
        }

        JPanel table = new PanelBuilder().grid(COLS, 0, 2, cells.toArray(new JComponent[0])).buildPanel();
        table.setOpaque(false);

        return table;
    }

    private static Color rowColor(int rank) {
        return switch (rank) {
            case 1  -> Fonts.gold;
            case 2  -> Fonts.silver;
            case 3  -> Fonts.bronze;

            default -> Fonts.mesos_shadow_red_low_opacity;
        };
    }

    @Override
    public void showError(String error) {
    }
}