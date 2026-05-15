package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.action.GUIOfferPickAction;
import it.polimi.ingsw.view.gui.action.GUICardPickAction;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;

public class GameInfoSection implements GUISection {
    private final JPanel panel;

    private final JLabel currentEra;
    private final JLabel currentPlayer;

    private final JButton pickCardsButton;
    private final JButton pickOfferButton;

    public GameInfoSection(ClientController clientController, CardPicksListener topListener, CardPicksListener bottomListener, OfferPickListener offerListener) {
        JLabel title = WidgetFactory.createLabel("Game Info");

        currentEra = WidgetFactory.createLabel("");
        currentPlayer = WidgetFactory.createLabel("");
        JPanel infoPanel = new PanelBuilder().column(1, title, currentEra, currentPlayer).buildPanel();

        JLabel orderTile = WidgetFactory.createImageLabel("/images/orderTiles/"+clientController.getCurrLobby().getPlayerCount()+".png",
                180, 270);

        pickCardsButton = WidgetFactory.createButton(new GUICardPickAction(clientController, topListener, bottomListener));
        pickOfferButton = WidgetFactory.createButton(new GUIOfferPickAction(clientController, offerListener));
        JPanel buttonsPanel = new PanelBuilder().column(1, pickCardsButton, pickOfferButton).buildPanel();

        panel = new PanelBuilder().column(5, infoPanel, orderTile, buttonsPanel)
                .withColor(Fonts.other_red).buildPanel();
    }

    @Override
    public void render(ClientController controller, JPanel container) {
        currentEra.setText(String.format("Current era: %3d", controller.getCurrLobby().getTurnState().getEra()));
        currentPlayer.setText(String.format("Current player: %3s", controller.getCurrLobby().getTurnState().getCurrPlayer().getName()));

        pickOfferButton.setEnabled(controller.getCurrLobby().getTurnState().canPickOffer()
                && controller.getCurrLobby().getCurrPlayer().equals(controller.getCurrLobby().getPlayer(controller.getID())));

        pickCardsButton.setEnabled(controller.getCurrLobby().getTurnState().canPickCard()
                && controller.getCurrLobby().getCurrPlayer().equals(controller.getCurrLobby().getPlayer(controller.getID())));

    }

    @Override
    public boolean isVisible(ClientController controller) {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
