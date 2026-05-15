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

public class LobbyInfoSection implements GUISection{
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
    private final JPanel topBar;

    public LobbyInfoSection(ClientController clientController) {
        title = WidgetFactory.createLabel("Lobby Info");

        lobbyID = WidgetFactory.createLabel("");
        count = WidgetFactory.createLabel("");

        model = new DefaultListModel<>();
        players = new JList<Map.Entry<Player, Boolean>>();
        players.setOpaque(false);
        players.setFont(Fonts.small);
        players.setModel(model);

        players.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JPanel cell = new JPanel();
            JLabel playerName = WidgetFactory.createLabel(String.format("%s - %s (ID: %3s)",
                    value.getKey().getName(), value.getKey().getTotem(), value.getValue() != null  ? value.getValue() : "Disconnected"));
            playerName.setFont(Fonts.medium);
            playerName.setForeground(Color.WHITE);
            cell.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            cell.add(playerName);
            cell.setOpaque(false);
            return cell;
        });

        topBar = new PanelBuilder().border(null, title, null, null,null).buildPanel();

        totemLabel = WidgetFactory.createLabel("Totem:");
        JComboBox<Totem> totemBox = WidgetFactory.createBox(Totem.values());
        totemBox.setMaximumSize(new Dimension(200,30));

        totemSelect = new PanelBuilder().row(5, totemLabel, totemBox)
                .centered()
                .withPadding(40,0,0,0)
                .buildPanel();

        totemSelect.setMaximumSize(totemSelect.getPreferredSize());
        totemSelect.setVisible(false);

        info = new PanelBuilder().rounded(30,5,Fonts.select, lobbyID, count, players,totemSelect)
                .centered()
                .buildPanel();

        join = WidgetFactory.createButton(new GUIJoinLobbyAction(clientController,totemBox));
            leave = WidgetFactory.createButton(new GUILeaveLobbyAction(clientController));
            start = WidgetFactory.createButton(new GUIStartLobbyAction(clientController));

        bottomBar = new PanelBuilder().grid(3, 10, 0, join, leave, start)
                .buildPanel();

        panel = new PanelBuilder().border(topBar,info,bottomBar,null,null).buildPanel();
    }

    @Override
    public void render(ClientController clientController, JPanel container) {
        model.clear();

        for (Map.Entry<Player, Boolean> entry : clientController.getCurrLobby().getPlayers().entrySet())
            model.addElement(entry);

        lobbyID.setText(String.format("Lobby ID: %3d", clientController.getCurrLobby().getLobbyID()));
        count.setText(String.format("Player Count: %3d/%3d",
                clientController.getCurrLobby().getPlayerCount(), clientController.getCurrLobby().getSize()));

        boolean isInLobby = clientController.getCurrLobby().getPlayer(clientController.getID()) != null;

        totemSelect.setVisible(!isInLobby);


    }

    @Override
    public boolean isVisible(ClientController clientController) {
        return clientController.getCurrLobby() != null && !clientController.getCurrLobby().getPlayers().isEmpty();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}

