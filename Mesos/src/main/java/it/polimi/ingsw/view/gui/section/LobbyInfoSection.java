package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.action.GUIJoinLobbyAction;
import it.polimi.ingsw.view.gui.action.GUILeaveLobbyAction;
import it.polimi.ingsw.view.gui.action.GUIStartLobbyAction;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class LobbyInfoSection implements GUISection {
    private final JPanel panel;

    private final JLabel title;

    private final JPanel info;
    private final JLabel lobbyID;
    private final JLabel count;
    private final JList<Map.Entry<Player, Boolean>> players;
    private final DefaultListModel<Map.Entry<Player, Boolean>> model;

    private final JPanel bottomBar;
    private final JButton join;
    private final JButton leave;
    private final JButton start;
    private final JPanel totemSelect;
    private final JLabel totemLabel;

    public LobbyInfoSection(ClientController clientController) {
        title = WidgetFactory.createLabel("Lobby Info");

        lobbyID = WidgetFactory.createLabel("");
        count = WidgetFactory.createLabel("");

        model = new DefaultListModel<>();
        players = WidgetFactory.createJList(model,
                value -> String.format("%10s - %5s (%s)",
                        value.getKey().getName(), value.getKey().getTotem(),
                        value.getValue() ? "Connected" : "Disconnected"));


        totemLabel = WidgetFactory.createLabel("Totem: ");
        JComboBox<Totem> totemBox = WidgetFactory.createBox(Totem.values(), 200, 30);
        totemSelect = new PanelBuilder().row(5, totemLabel, totemBox)
                .centered()
                .withPadding(40, 0, 0, 0)
                .buildPanel();

        info = new PanelBuilder()
                .rounded(30, 5, Fonts.select, lobbyID, count, players, totemSelect)
                .centered()
                .buildPanel();

        join = WidgetFactory.createButton(new GUIJoinLobbyAction(clientController, totemBox));
        leave = WidgetFactory.createButton(new GUILeaveLobbyAction(clientController));
        start = WidgetFactory.createButton(new GUIStartLobbyAction(clientController));
        bottomBar = new PanelBuilder()
                .grid(3, 10, 0, join, leave, start)
                .buildPanel();

        panel = new PanelBuilder().border(title, info, bottomBar, null, null).buildPanel();
    }

    @Override
    public void render(ClientController clientController, JPanel container) {
        model.clear();

        if (clientController.getCurrLobby() == null) {
            panel.setVisible(false);
            return;
        }

        panel.setVisible(true);
        for (Map.Entry<Player, Boolean> entry : clientController.getCurrLobby().getPlayers().entrySet())
            model.addElement(entry);

        lobbyID.setText(String.format("Lobby ID: %3d", clientController.getCurrLobby().getLobbyID()));
        count.setText(String.format("Player Count: %3d/%3d",
                clientController.getCurrLobby().getPlayerCount(), clientController.getCurrLobby().getSize()));

        boolean isInLobby = false;
        for (Player p : clientController.getCurrLobby().getPlayers().keySet()) {
            if (p.getName().equals(clientController.getID())) {
                isInLobby = true;
                break;
            }
        }

        boolean isFull = clientController.getCurrLobby().getPlayerCount() == clientController.getCurrLobby().getSize();

        totemSelect.setVisible(!isInLobby);

        join.setEnabled(!isInLobby);
        leave.setEnabled(isInLobby);
        start.setEnabled(isFull && isInLobby);
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}

