package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.PlayerLabels;
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

        tabbedPane = WidgetFactory.createTab();

        playerLabelsMap = new HashMap<>();

        for (Player p : clientController.getCurrLobby().getPlayers().keySet()) {
            PlayerLabels labels = new PlayerLabels(p.getName());
            JPanel playerTabContainer = buildSinglePlayerTab(labels);

            playerLabelsMap.put(p, labels);
            tabbedPane.addTab(p.getName(), playerTabContainer);
        }

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel buildSinglePlayerTab(PlayerLabels labels) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JPanel row1 = new PanelBuilder().row(0, labels.name, labels.pp).buildPanel();
        JPanel row2 = new PanelBuilder().row(0, labels.food, labels.fullSet, labels.sustenanceDiscount).buildPanel();
        JPanel row3 = new PanelBuilder().row(0, labels.stars, labels.builderDiscount, labels.uniqueInventors).buildPanel();
        JPanel row4 = new PanelBuilder().row(0, labels.collector, labels.hunter, labels.builder).buildPanel();
        JPanel row5 = new PanelBuilder().row(0, labels.shaman, labels.artist, labels.inventor).buildPanel();

        JSeparator sep1 = WidgetFactory.createSeparator();
        JSeparator sep2 = WidgetFactory.createSeparator();
        JSeparator sep3 = WidgetFactory.createSeparator();
        JSeparator sep4 = WidgetFactory.createSeparator();


        setRowsMaxSize(row1, row2, row3, row4, row5);

        JPanel infoBox = new PanelBuilder()
                .rounded(25, 12, new Color(0x5D2030), row1, sep1, row2, sep2, row3, sep3, row4, sep4, row5)
                .buildPanel();

        infoBox.setMaximumSize(new Dimension(600, 500));
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

        for (Player player : controller.getCurrLobby().getPlayers().keySet()) {
            PlayerLabels labels = playerLabelsMap.get(player);

            if (labels != null) {
                setLabels(labels, player);
            }
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }


    public void setLabels(PlayerLabels labels, Player p){
        labels.pp.setText("PP: " + p.getPP());
        labels.food.setText("Food: " + p.getFood());
        labels.fullSet.setText("Full Set: " + p.getTribe().getMinChar());
        labels.sustenanceDiscount.setText("Sustenance Discount: " + p.getTribe().getSustenanceDiscount());
        labels.stars.setText("Stars: " + p.getTribe().getStars());
        labels.builderDiscount.setText("Builder Discount: " + p.getTribe().getBuilderDiscount());
        labels.uniqueInventors.setText("Unique inventors: " + p.getTribe().getUniqueInventorsCount());
        labels.collector.setText("Collectors: " + p.getTribe().getCollectorCount());
        labels.hunter.setText("Hunters: " + p.getTribe().getHunterCount());
        labels.builder.setText("Builders: " + p.getTribe().getBuilderCount());
        labels.shaman.setText("Shamans: " + p.getTribe().getShamanCount());
        labels.artist.setText("Artists: " + p.getTribe().getArtistCount());
        labels.inventor.setText("Inventors: " + p.getTribe().getInventorCount());
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