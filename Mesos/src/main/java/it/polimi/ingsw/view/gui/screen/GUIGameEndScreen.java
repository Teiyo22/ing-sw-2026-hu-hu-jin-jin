package it.polimi.ingsw.view.gui.screen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.util.Fonts;

class GUIGameEndScreen extends GUIScreen implements ActionListener {
    private static final int COLS = 4;
    private static final int CELL_HEIGHT = 60;

    private static final String[] HEADERS = { "RANK", "PLAYER NAME", "PRESTIGE POINTS", "FOOD" };

    public GUIGameEndScreen(JFrame frame, ClientController clientController) {
        super(frame, clientController);
    }

    public void render() {
        JPanel panel= new JPanel(new BorderLayout());
        panel.setBackground(Fonts.red);
        panel.setBorder(new EmptyBorder(40, 60, 40, 60));

        panel.add(buildTitle(),     BorderLayout.NORTH);
        panel.add(buildTable(),     BorderLayout.CENTER);

        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private JLabel buildTitle() {
        JLabel title = new JLabel("FINAL RANKING", SwingConstants.CENTER);

        title.setFont(Fonts.large);
        title.setForeground(Fonts.parchment);
        title.setBorder(new EmptyBorder(0, 0, 30, 0));

        return title;
    }

    private JPanel buildTable() {
        int rows = 1 + clientController.getCurrLobby().getPlayers().size();

        JPanel grid = new JPanel(new GridLayout(rows, COLS, 0, 2));
        grid.setBackground(Fonts.black);

        for (String header : HEADERS) {
            grid.add(cell(header, Fonts.black, Color.WHITE, true));
        }

        for (Player player: clientController.getCurrLobby().getPlayers().keySet()){
            Color rowColor = rowColor(player.getRank());

            grid.add(cell(String.valueOf(player.getRank()), rowColor, Color.BLACK, false));
            grid.add(cell(player.getName(), rowColor, Color.BLACK, false));
            grid.add(cell(""+ player.getPP() , rowColor, Color.BLACK, false));
            grid.add(cell(""+ player.getFood() , rowColor, Color.BLACK, false));
        }

        return grid;
    }

    private JLabel cell(String text, Color rowColor, Color textColor, boolean isHeader) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);

        label.setFont(isHeader ? Fonts.small.deriveFont(Font.BOLD) : Fonts.small);
        label.setBackground(rowColor);
        label.setForeground(textColor);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(0, CELL_HEIGHT));
        label.setBorder(new EmptyBorder(0, 10, 0, 10));
        return label;
    }

    private static Color rowColor(int rank) {
        return switch (rank) {
            case 1  -> Fonts.gold;
            case 2  -> Fonts.silver;
            case 3  -> Fonts.bronze;
            default -> Fonts.parchment;
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}