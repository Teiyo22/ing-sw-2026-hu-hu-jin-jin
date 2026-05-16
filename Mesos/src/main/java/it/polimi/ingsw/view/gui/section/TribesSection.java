package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TribesSection implements GUISection {
    private final JPanel mainPanel;
    private final JTabbedPane tabbedPane;
    private final Map<Player, PlayerLabels> playerLabelsMap;

    public TribesSection(ClientController clientController) {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(Fonts.small.deriveFont(Font.BOLD));
        tabbedPane.setBackground(Color.BLACK);
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setOpaque(false);

        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPane, int itemIndex, int x, int y, int w, int h, boolean isSelected) {
                g.setColor(isSelected ? Color.BLACK : Color.DARK_GRAY);
                g.fillRect(x, y, w, h);
            }
        });

        playerLabelsMap = new HashMap<>();

        for (Player p : clientController.getCurrLobby().getPlayers().keySet()) {
            PlayerLabels labels = new PlayerLabels();
            JPanel playerTabContainer = buildSinglePlayerTab(p.getName(), labels);

            playerLabelsMap.put(p, labels);
            tabbedPane.addTab(p.getName(), playerTabContainer);
        }

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel buildSinglePlayerTab(String playerName, PlayerLabels labels) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        createLabels(playerName, labels);

        JPanel row1 = new PanelBuilder().row(0, labels.name, labels.pp).buildPanel();

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0x888888));

        JPanel row2 = new PanelBuilder().row(0, labels.food, labels.fullSet, labels.sustenanceDiscount).buildPanel();

        row1.setMaximumSize(new Dimension(480, 40));
        row2.setMaximumSize(new Dimension(480, 40));
        sep.setMaximumSize(new Dimension(460, 1));

        JPanel infoBox = new PanelBuilder()
                .rounded(25, 12, new Color(0x5D2030), row1, sep, row2)
                .buildPanel();

        infoBox.setMaximumSize(new Dimension(520, 160));
        infoBox.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        container.add(infoBox);
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        return container;
    }

    public void setRowsMaxSize(JPanel...rows){
        for(JPanel row: rows){
            row.setMaximumSize(new Dimension(600, 60));
        }
    }

    @Override
    public void render(ClientController controller, JPanel container) {

        for (Player p : controller.getCurrLobby().getPlayers().keySet()) {
            PlayerLabels labels = playerLabelsMap.get(p);

            if (labels != null) {
                labels.pp.setText("PP: " + p.getPP());
                labels.food.setText("FOOD: " + p.getFood());

                labels.fullSet.setText("FULL SET: " + p.getTribe().getMinChar());
                labels.sustenanceDiscount.setText("SUSTENANCE DISCOUNT: " + p.getTribe().getSustenanceDiscount());
            }
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    @Override
    public boolean isVisible(ClientController controller) {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }
}