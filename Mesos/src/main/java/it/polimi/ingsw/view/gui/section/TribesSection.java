package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.view.ImageCache;
import it.polimi.ingsw.utils.view.PanelBuilder;
import it.polimi.ingsw.view.gui.components.PlayerCards;
import it.polimi.ingsw.view.gui.components.PlayerInfo;
import it.polimi.ingsw.utils.view.WidgetFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TribesSection extends GUISection {
    private final JTabbedPane tabbedPane;
    private final Map<Player, PlayerInfo> playerLabelsMap;
    private final Map<Player, PlayerCards> playerCardsMap;

    public TribesSection(ClientController clientController, ImageCache imageCache) {
        tabbedPane = WidgetFactory.createTab();

        playerLabelsMap = new HashMap<>();
        playerCardsMap = new HashMap<>();

        for (Player p : clientController.getCurrLobby().getPlayers().keySet()) {
            PlayerInfo labels = new PlayerInfo();
            PlayerCards cards = new PlayerCards(imageCache);
            JPanel playerTabContainer = buildSinglePlayerTab(labels, cards);

            playerLabelsMap.put(p, labels);
            playerCardsMap.put(p, cards);
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
        Lobby currLobby = controller.getCurrLobby();
        if (currLobby != null)
            for (Player p : currLobby.getPlayers().keySet()) {
                playerLabelsMap.get(p).renderLabels(p);
                playerCardsMap.get(p).renderCards(p);
            }
    }
}