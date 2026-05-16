package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.section.*;
import it.polimi.ingsw.view.gui.util.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class GUIGamePlayScreen extends GUIScreen{
    private JPanel topRowPanel;
    private JPanel bottomRowPanel;
    private JPanel offerTrackPanel;
    private JPanel gameInfoPanel;

    private final List<GUISection> sections;

    public GUIGamePlayScreen(GUIView frame, ClientController controller) {
        super(frame, controller);

        CardPicksListener topListener = new CardPicksListener();
        CardPicksListener bottomListener = new CardPicksListener();

        OfferPickListener offerPickListener = new OfferPickListener();

        sections = List.of(new TopRowSection(topListener),
                new BottomRowSection(bottomListener),
                new OfferTrackSection(controller, offerPickListener),
                new GameInfoSection(clientController, topListener, bottomListener, offerPickListener),
                new TribesSection(clientController));

        topRowPanel = sections.get(0).getPanel();
        bottomRowPanel = sections.get(1).getPanel();
        offerTrackPanel = sections.get(2).getPanel();
        gameInfoPanel = sections.get(3).getPanel();

        JPanel board = new PanelBuilder().border(topRowPanel, offerTrackPanel, bottomRowPanel, null, null).buildPanel();
        JPanel infoPanel = new PanelBuilder().column(0, gameInfoPanel).withPadding(30, 30, 30, 0).buildPanel();

        board.setOpaque(false);
        infoPanel.setOpaque(false);

        JPanel container = new JPanel(new BorderLayout());
        container.add(infoPanel, BorderLayout.WEST);
        container.add(board, BorderLayout.CENTER);
        container.setOpaque(false);


        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(Color.BLACK);
        tabs.setForeground(Color.WHITE);
        tabs.setOpaque(false);
        tabs.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPane, int itemIndex, int x, int y, int w, int h, boolean isSelected) {
                g.setColor(isSelected ? Color.BLACK : Color.DARK_GRAY);
                g.fillRect(x, y, w, h);
            }
        });

        tabs.addTab("Game", container);

        JPanel tribesPanel = sections.get(4).getPanel();
        tabs.addTab("Tribes", tribesPanel);

        this.setLayout(new BorderLayout());
        this.add(tabs, BorderLayout.CENTER);
    }

    @Override
    public void render() {
        sections.stream()
                .filter(s -> s.isVisible(clientController))
                .forEach(s -> s.render(clientController, this));

        frame.setContentPane(this);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showError(String error) {

    }
}
