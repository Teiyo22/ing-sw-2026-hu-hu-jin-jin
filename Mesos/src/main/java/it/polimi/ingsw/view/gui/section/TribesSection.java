package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.util.*;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TribesSection extends GUISection {
    private final JTabbedPane tabbedPane;
    private final List<PlayerInfo> playerLabelsList;
    private final List<PlayerCards> playerCardsList;

    public TribesSection(ClientController clientController, ImageCache imageCache) {
        tabbedPane = WidgetFactory.createTab();

        playerLabelsList = new ArrayList<>();
        playerCardsList = new ArrayList<>();

        for (Player p : clientController.getCurrLobby().getPlayers().keySet()) {
            PlayerInfo labels = new PlayerInfo(p);
            PlayerCards cards = new PlayerCards(p, imageCache);
            JPanel playerTabContainer = buildSinglePlayerTab(labels, cards);

            playerLabelsList.add(labels);
            playerCardsList.add(cards);
            tabbedPane.addTab(p.getName(), playerTabContainer);
        }

        panel = new PanelBuilder().border(null, tabbedPane, null, null, null).buildPanel();
    }

    private JPanel buildSinglePlayerTab(PlayerInfo info, PlayerCards cards) {
        JPanel infoColumn = new PanelBuilder()
            .grid(1, 0, 5, info.getRows())
            .withPadding(5, 5, 5, 5)
            .buildPanel();

        JPanel cardColumn = new PanelBuilder()
            .grid(1, 0, 5, cards.getRows())
            .withPadding(5, 5, 5, 5)
            .buildPanel();

        return new PanelBuilder().border(null, cardColumn, null, infoColumn, null).buildPanel();
    }

    @Override
    public void render(ClientController controller) {
        for (PlayerInfo playerInfo : playerLabelsList)
            playerInfo.renderLabels();

        for(PlayerCards playerCards : playerCardsList)
            playerCards.renderCards();
    }
}