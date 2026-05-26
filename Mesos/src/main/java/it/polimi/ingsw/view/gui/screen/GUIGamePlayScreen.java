package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.section.*;
import it.polimi.ingsw.view.gui.util.ImageCache;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIGamePlayScreen extends GUIScreen {
    public GUIGamePlayScreen(GUIView frame, ClientController controller) {
        super(frame, controller);
        this.background = ImageCache.loadImage("/images/mesos_blurred.png");

        ImageCache imageCache = new ImageCache();
        int maxCardCount = getMaxCardCount(controller.getCurrLobby().getSize());

        RowSection topRowSection = new TopRowSection(imageCache, maxCardCount);
        RowSection bottomRowSection = new BottomRowSection(imageCache, maxCardCount);
        OfferTrackSection offerTrackSection = new OfferTrackSection(controller, imageCache);

        sections = List.of(
            topRowSection,
            bottomRowSection,
            offerTrackSection,
            new GameInfoSection(clientController, topRowSection, bottomRowSection, offerTrackSection, imageCache),
            new TribesSection(clientController, imageCache)
        );

        JPanel topRowPanel = sections.get(0).getPanel();
        JPanel bottomRowPanel = sections.get(1).getPanel();
        JPanel offerTrackPanel = sections.get(2).getPanel();
        JPanel gameInfoPanel = sections.get(3).getPanel();
        JPanel tribesPanel = sections.get(4).getPanel();

        JPanel board = new PanelBuilder()
            .border(topRowPanel, offerTrackPanel, bottomRowPanel, null, null)
            .withPadding(30, 5, 30, 5)
            .buildPanel();

        JPanel gameInfoContainer = new PanelBuilder().column(0, gameInfoPanel)
            .withPadding(30, 30, 30, 0)
            .buildPanel();

        JPanel gamePanel = new PanelBuilder()
            .border(null, board, null, gameInfoContainer, null)
            .buildPanel();

        JTabbedPane tabs = WidgetFactory.createTab();
        tabs.addTab("Game", gamePanel);
        tabs.addTab("Tribes", tribesPanel);

        this.setLayout(new BorderLayout());
        this.add(tabs, BorderLayout.CENTER);
    }


    private int getMaxCardCount(int lobbySize) {
        int maxDrawn = lobbySize + 4;
        int maxBuildings = (int) Math.ceil((double) lobbySize / 2);
        return maxDrawn + maxBuildings;
    }
}
