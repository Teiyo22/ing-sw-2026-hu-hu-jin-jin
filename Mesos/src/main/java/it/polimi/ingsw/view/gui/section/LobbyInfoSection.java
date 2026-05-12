package it.polimi.ingsw.view.gui.section;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.gui.action.GUIJoinLobbyAction;
import it.polimi.ingsw.view.gui.action.GUILeaveLobbyAction;
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
    private final JList<Map.Entry<Player, Integer>> players;
    private final DefaultListModel<Map.Entry<Player, Integer>> model;

    private final JPanel bottomBar;
    private final JButton join;
    private final JButton leave;
    private final JButton start;

    public LobbyInfoSection(ClientController clientController) {
        title = WidgetFactory.createLabel("Lobby Info");

        lobbyID = WidgetFactory.createLabel("");
        count = WidgetFactory.createLabel("");

        model = new DefaultListModel<>();
        players = new JList<>();
        players.setBackground(Fonts.cream);
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
            cell.setOpaque(true);
            return cell;
        });


        info = new PanelBuilder().column(0, lobbyID, count, players).buildPanel();

        join = WidgetFactory.createButton(new GUIJoinLobbyAction(clientController));
        leave = WidgetFactory.createButton(new GUILeaveLobbyAction(clientController));
        start = new JButton("Start");

        bottomBar = new PanelBuilder().grid(3, 10, 0, join, leave, start).buildPanel();

        panel = new PanelBuilder().border(title, info, bottomBar, null, null)
                .withColor(Fonts.other_red)
                .buildPanel();
    }

    @Override
    public void render(ClientController clientController, JPanel container) {
        model.clear();

        for (Map.Entry<Player, Integer> entry : clientController.getCurrLobby().getPlayers().entrySet())
            model.addElement(entry);

        lobbyID.setText(String.format("Lobby ID: %3d", clientController.getCurrLobby().getLobbyID()));
        count.setText(String.format("Player Count: %3d/%3d",
                clientController.getCurrLobby().getPlayerCount(), clientController.getCurrLobby().getSize()));
/*        Lobby lobby = clientController.getCurrLobby();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Fonts.black);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Lobby" + lobby.getLobbyID());
        title.setFont(Fonts.large);
        c.gridy = 0;
        panel.add(title, c);

        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.setBackground(Fonts.grey);

        for (Player p : lobby.getPlayers().keySet()) {
            JLabel infoP = new JLabel("● " + p.getName() + " - " + p.getTotem());
            infoP.setFont(Fonts.small);
            infoP.setForeground(Color.WHITE);
            playersPanel.add(infoP);
        }
        c.gridy++;
        panel.add(playersPanel, c);

        JLabel name = new JLabel("Name :");
        name.setFont(Fonts.small);
        c.gridx = 0; c.gridy++; c.anchor = GridBagConstraints.EAST;
        panel.add(name, c);

        JTextField nameText= new JTextField();
        nameText.setBackground(Color.WHITE);
        nameText.setPreferredSize(new Dimension(200, 30));
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        panel.add(nameText, c);

        JLabel totemLabel = new JLabel("Totem :");
        totemLabel.setFont(Fonts.small);
        c.gridx = 0; c.gridy++; c.anchor = GridBagConstraints.EAST;
        panel.add(totemLabel, c);

        JComboBox<Totem> totemBox = new JComboBox<>();
        totemBox.setFont(Fonts.small);
        totemBox.setPreferredSize(new Dimension(200, 30));
        List<Totem> totiPresi = new ArrayList<>();
        for (Player p : lobby.getPlayers().keySet())
            totiPresi.add(p.getTotem());
        for (Totem t : Totem.values())
            if (!totiPresi.contains(t))
                totemBox.addItem(t);
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        panel.add(totemBox, c);

        JButton join = new JButton("Join");
        join.setFont(new Font("Arial", Font.PLAIN, 30));
        join.setContentAreaFilled(false);
        join.setForeground(Color.WHITE);

        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.PLAIN, 30));
        exit.setContentAreaFilled(false);
        exit.setForeground(Color.WHITE);

        JButton start = new JButton("Start");
        start.setFont(new Font("Arial", Font.PLAIN, 30));
        start.setContentAreaFilled(false);
        start.setForeground(Color.WHITE);

        boolean alreadyJoined = lobby.getPlayers().containsKey(clientController.getID());
        join.setEnabled(!alreadyJoined);
        exit.setEnabled(alreadyJoined);
        start.setEnabled(lobby.getPlayers().size() == lobby.getSize());

        join.addActionListener(e -> {
            if (nameText.getText().isBlank()) return;
            new JoinLobbyCommand(clientController, lobby.getLobbyID(),
                    new Player(nameText.getText(), (Totem) totemBox.getSelectedItem())).execute();
        });

        exit.addActionListener(e -> {
            try {
                clientController.getServer().leaveLobby(clientController.getID(), lobby.getLobbyID());
                clientController.getServer().getLobbyInfo(clientController.getID(), lobby.getLobbyID());
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(container, "Errore: " + ex.getMessage());
            }
        });

        start.addActionListener(e -> {
            new StartLobbyCommand(clientController, lobby.getLobbyID()).execute();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(join);
        buttonPanel.add(exit);
        buttonPanel.add(start);

        c.gridx = 0; c.gridy++; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(40, 0, 0, 0);
        panel.add(buttonPanel, c);

        container.add(panel, BorderLayout.CENTER);*/
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

