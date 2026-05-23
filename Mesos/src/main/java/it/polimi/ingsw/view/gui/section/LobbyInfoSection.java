package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.action.GUIJoinLobbyAction;
import it.polimi.ingsw.view.gui.action.GUILeaveLobbyAction;
import it.polimi.ingsw.view.gui.action.GUIStartLobbyAction;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.util.Map;

public class LobbyInfoSection extends GUISection {
    private JLabel lobbyID;
    private JLabel count;

    private DefaultListModel<Map.Entry<Player, Boolean>> model;

    private JPanel totemSelect;
    private JComboBox<Totem> totemBox;

    private final JButton join;
    private final JButton leave;
    private final JButton start;

    public LobbyInfoSection(ClientController clientController) {
        JLabel title = WidgetFactory.mediumLabel("Lobby Info");
        JPanel infoPanel = createInfoPanel();

        join = WidgetFactory.mediumButton(new GUIJoinLobbyAction(clientController, totemBox));
        leave = WidgetFactory.mediumButton(new GUILeaveLobbyAction(clientController));
        start = WidgetFactory.mediumButton(new GUIStartLobbyAction(clientController));
        JPanel bottomBar = new PanelBuilder()
                .grid(3, 10, 0, join, leave, start)
                .buildPanel();

        panel = new PanelBuilder()
                .border(title, infoPanel, bottomBar, null, null)
                .buildPanel();
        panel.setVisible(false);
    }

    @Override
    public void render(ClientController clientController) {
        model.clear();

        Lobby currLobby = clientController.getCurrLobby();
        if (currLobby == null) {
            panel.setVisible(false);
            return;
        }

        panel.setVisible(true);
        model.addAll(currLobby.getPlayers().entrySet());

        lobbyID.setText(String.format("【 # %03d 】", currLobby.getLobbyID()));
        count.setText(String.format("【 ♟ %d / %d 】", currLobby.getPlayerCount(), currLobby.getSize()));

        boolean isInLobby = currLobby.containsClient(clientController.getID());
        boolean isFull = currLobby.getPlayerCount() == currLobby.getSize();

        totemSelect.setVisible(!isInLobby && !isFull);

        join.setEnabled(!isInLobby && !isFull);
        leave.setEnabled(isInLobby);
        start.setEnabled(isFull && isInLobby);
    }

    private String formatPlayer(Map.Entry<Player, Boolean> player) {
        Map<Totem, String> totemColor = Map.of(
                Totem.RED, "#e74c3c", Totem.BLUE, "#2980b9", Totem.BLACK, "#2c2c2c",
                Totem.WHITE, "#ffffff", Totem.YELLOW, "#f1c40f"
        );

        return String.format("<html>%s <font color='%s'>♦ %s </font></html>",
                player.getKey().getName(),
                totemColor.get(player.getKey().getTotem()),
                player.getKey().getTotem());
    }

    private String formatConnectionStatus(Map.Entry<Player, Boolean> player) {
        return player.getValue() ? "Connected ● " : "Disconnected ○ ";
    }

    private JPanel createInfoPanel() {
        //  Generic info panel creation (ID, player count)
        lobbyID = WidgetFactory.mediumLabel("");
        count = WidgetFactory.mediumLabel("");
        JPanel genericInfoPanel = new PanelBuilder()
                .rounded(20)
                .grid(1, 0, 0, lobbyID, count)
                .withPadding(20, 20, 20, 20)
                .withTranslucentColor(Fonts.select)
                .buildPanel();


        // Player List Creation
        model = new DefaultListModel<>();
        JList<Map.Entry<Player, Boolean>> players = WidgetFactory.list(model, 5,
                this::formatPlayer,
                this::formatConnectionStatus,
                null);


        // Totem selection panel creation
        JLabel totemLabel = WidgetFactory.mediumLabel("Totem: ");
        totemBox = WidgetFactory.comboBox(Totem.values(), 200, 30);
        totemSelect = new PanelBuilder().row(5, totemLabel, totemBox)
                .centered()
                .withPadding(40, 0, 0, 0)
                .buildPanel();

        return new PanelBuilder()
                .rounded(30)
                .withTranslucentColor(Fonts.select)
                .border(0, 20, genericInfoPanel, players, totemSelect, null, null)
                .withPadding(20, 20, 20, 20)
                .buildPanel();
    }
}

