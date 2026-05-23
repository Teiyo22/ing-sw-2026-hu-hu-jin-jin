package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.action.GUILeaveLobbyAction;
import it.polimi.ingsw.view.gui.action.GUIOfferPickAction;
import it.polimi.ingsw.view.gui.action.GUICardPickAction;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.components.OrderTileComponent;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GameInfoSection extends GUISection {
    private JLabel currentEra;
    private JLabel currentPlayer;
    private Map<Player, JLabel> playerEntries;

    private final OrderTileComponent orderTile;

    private final JButton pickCardsButton;
    private final JButton pickOfferButton;

    public GameInfoSection(ClientController clientController, CardPicksListener topListener, CardPicksListener bottomListener,
                           OfferPickListener offerListener, Map<Totem, Image> totemIcons) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Player action panel
        pickCardsButton = WidgetFactory.tinyButton(new GUICardPickAction(clientController, topListener, bottomListener));
        pickCardsButton.setEnabled(false);
        pickOfferButton = WidgetFactory.tinyButton(new GUIOfferPickAction(clientController, offerListener));
        pickOfferButton.setEnabled(false);
        orderTile = new OrderTileComponent(clientController, totemIcons);

        JPanel playerActionPanel = new PanelBuilder()
                .column(5, pickCardsButton, pickOfferButton, orderTile)
                .buildPanel();

        // Info panel
        JPanel infoPanel = createInfoPanel(clientController);

        // Leave button
        JButton leaveButton = WidgetFactory.tinyButton(new GUILeaveLobbyAction(clientController));
        JPanel leavePanel = new PanelBuilder().column(0, leaveButton).buildPanel();

        panel = new PanelBuilder().rounded(30)
                .border(playerActionPanel, infoPanel, leavePanel, null, null)
                .withTranslucentColor(Fonts.mesos_shadow_red_low_opacity)
                .withPadding(30, 10, 30, 10)
                .size(new Dimension((int) (screenSize.width * 0.15), screenSize.height))
                .buildPanel();
    }

    @Override
    public void render(ClientController controller) {
        Lobby currLobby = controller.getCurrLobby();

        if (currLobby == null)
            return;

        if (currLobby.getTurnState() != null) {
            currentEra.setText(String.format("<html><b>Current era</b>: %d</html>", currLobby.getTurnState().getEra()));
            currentPlayer.setText(String.format("<html><b>Current player</b>: %s</html>", currLobby.getTurnState().getCurrPlayer().getName()));
            pickOfferButton.setEnabled(currLobby.getTurnState().canPickOffer());
            pickCardsButton.setEnabled(currLobby.getTurnState().canPickCard());
        }

        for (Player p : currLobby.getPlayers().keySet()) {
            playerEntries.get(p).setText(String.format("<html><b>%s [%s]</b><br>&emsp;PP: %d | Food: %d</html>",
                    p.getName(), p.getTotem(), p.getPP(), p.getFood()));
        }

        orderTile.update(controller);
    }

    private JPanel createInfoPanel(ClientController clientController) {
        // Game info panel
        JLabel divider = WidgetFactory.monospacedLabel("--------------------");
        JLabel gameInfoLabel = WidgetFactory.smallLabel("Game Info");
        currentEra = WidgetFactory.monospacedLabel("");
        currentPlayer = WidgetFactory.monospacedLabel("");
        JPanel gameInfoPanel = new PanelBuilder()
                .column(0, gameInfoLabel, currentEra, currentPlayer, divider)
                .buildPanel();

        // Player info panel
        playerEntries = clientController.getCurrLobby().getPlayers().keySet().stream()
                .map(p -> Map.entry(p, WidgetFactory.monospacedLabel("")))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        JPanel playerInfoPanel = new PanelBuilder()
                .column(0, playerEntries.values().toArray(new JLabel[0]))
                .buildPanel();

        // Info panel
        return new PanelBuilder()
                .column(10, orderTile, gameInfoPanel, playerInfoPanel)
                .buildPanel();
    }
}
