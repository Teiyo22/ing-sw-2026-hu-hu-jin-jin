package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.action.GUIOfferPickAction;
import it.polimi.ingsw.view.gui.action.GUICardPickAction;
import it.polimi.ingsw.view.gui.components.CardPicksListener;
import it.polimi.ingsw.view.gui.components.OfferPickListener;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInfoSection implements GUISection {
    private final JPanel panel;

    private final JLabel currentEra;
    private final JLabel currentPlayer;
    private final Map<Player, JLabel> playerEntries;

    private final JButton pickCardsButton;
    private final JButton pickOfferButton;

    public GameInfoSection(ClientController clientController, CardPicksListener topListener, CardPicksListener bottomListener, OfferPickListener offerListener) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension panelSize = new Dimension((int)  (screenSize.width*0.2), screenSize.height);

        //buttons panel
        pickCardsButton = WidgetFactory.createButton(new GUICardPickAction(clientController, topListener, bottomListener));
        pickCardsButton.setFont(Fonts.small);
        pickCardsButton.setBorderPainted(true);
        pickOfferButton = WidgetFactory.createButton(new GUIOfferPickAction(clientController, offerListener));
        pickOfferButton.setFont(Fonts.small);
        pickOfferButton.setBorderPainted(true);
        JPanel buttonsPanel = new PanelBuilder().column(10, pickCardsButton, pickOfferButton).buildPanel();

        //order tile
        JLabel orderTile = WidgetFactory.createImageLabel(getClass().getResource(
                        "/images/orderTiles/"+clientController.getCurrLobby().getPlayerCount()+".png"),
                (int) (screenSize.height * 0.35 * (2.0/3.0)), (int) (screenSize.height * 0.35));

        //infos panel
        List<JLabel> components = new ArrayList<>();

        JLabel divider = new JLabel("-------------------------");
        divider.setForeground(Color.WHITE);
        divider.setFont(Fonts.monospaced);

        JLabel generalInfoTitle = WidgetFactory.createLabel("Game Info");
        currentEra = WidgetFactory.createLabel("");
        currentEra.setFont(Fonts.monospaced);
        currentPlayer = WidgetFactory.createLabel("");
        currentPlayer.setFont(Fonts.monospaced);

        playerEntries = new HashMap<>();
        for(Player p : clientController.getCurrLobby().getPlayers().keySet()){
            JLabel entry = WidgetFactory.createLabel("");
            entry.setFont(Fonts.monospaced);
            playerEntries.put(p, entry);
        }

        components.add(generalInfoTitle);
        components.add(new JLabel(" "));
        components.add(currentEra);
        components.add(currentPlayer);
        components.add(new JLabel(" "));
        components.add(divider);
        components.add(new JLabel(" "));
        components.addAll(playerEntries.values());

        JPanel infoPanel = new PanelBuilder().column(0, components.toArray(new JLabel[0])).buildPanel();

        //putting together panels
        JPanel contentPanel = new PanelBuilder().column(20, buttonsPanel, orderTile, infoPanel).buildPanel();

        panel = new PanelBuilder().rounded(30, 10, Fonts.mesos_shadow_red_low_opacity, contentPanel)
                .withPadding(30, 10, 30, 10).buildPanel();

        panel.setPreferredSize(panelSize);
    }

    @Override
    public void render(ClientController controller, JPanel container) {
        currentEra.setText(String.format("<html><b>Current era</b>: %d</html>", controller.getCurrLobby().getTurnState().getEra()));
        currentPlayer.setText(String.format("<html><b>Current player</b>: %s</html>", controller.getCurrLobby().getTurnState().getCurrPlayer().getName()));

        for(Player p : controller.getCurrLobby().getPlayers().keySet()){
            playerEntries.get(p).setText(String.format("<html><b>%s</b><br>&emsp;PP: %d | Food: %d</html>",
                    p.getName(), p.getPP(), p.getFood()));
        }

        pickOfferButton.setEnabled(controller.getCurrLobby().getTurnState().canPickOffer()
                && controller.getCurrLobby().getCurrPlayer().equals(controller.getCurrLobby().getPlayer(controller.getID())));

        pickCardsButton.setEnabled(controller.getCurrLobby().getTurnState().canPickCard()
                && controller.getCurrLobby().getCurrPlayer().equals(controller.getCurrLobby().getPlayer(controller.getID())));

        panel.revalidate();
        panel.repaint();
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
