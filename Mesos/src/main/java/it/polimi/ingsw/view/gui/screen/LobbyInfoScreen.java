package it.polimi.ingsw.view.gui.screen;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LobbyInfoScreen extends GUIScreen implements ActionListener{
    private JPanel panel;
    private int lobbyID;
    private JButton join;
    private JButton exit;
    private JTextField nameText;
    private JComboBox<Totem> totemBox;
    private JPanel playersPanel;

    public LobbyInfoScreen(JFrame frame, ClientController clientController, JPanel panel, int lobbyID) {
        super(frame, clientController);
        this.panel = panel;
        this.lobbyID = lobbyID;
    }

    public void render() {
        Lobby lobby = clientController.getWaitingLobbies().get(lobbyID);
        Map<Integer, Player> players = lobby.getPlayers();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel(String.valueOf(lobbyID));
        title.setFont(Fonts.large);
        c.gridy = 0;
        panel.add(title, c);

        playersPanel = new JPanel();
        playersPanel.setBackground(Fonts.black);
        c.gridy++;
        panel.add(playersPanel, c);

        JLabel name = new JLabel("Name :");
        name.setFont(Fonts.small);
        c.gridx = 0; c.gridy++; c.anchor = GridBagConstraints.EAST;
        panel.add(name,c);

        nameText = new JTextField();
        nameText.addActionListener(this);
        nameText.setBackground(Color.WHITE);
        nameText.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        panel.add(nameText,c);

        JLabel totemLabel = new JLabel("Scegli totem:");
        totemLabel.setFont(Fonts.small);
        c.gridx = 0; c.gridy++; c.anchor = GridBagConstraints.EAST;
        panel.add(totemLabel, c);


        totemBox = new JComboBox<>();
        totemBox.setFont(Fonts.small);
        totemBox.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        panel.add(totemBox, c);


        join = new JButton("Join");
        join.setFont(Fonts.medium);
        join.setBorderPainted(false);
        join.setContentAreaFilled(false);
        join.setFocusable(false);
        join.setForeground(Color.BLACK);
        join.addActionListener(this);
        c.gridy++;
        panel.add(join, c);

        exit = new JButton("Exit");
        exit.setFont(Fonts.medium);
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setFocusable(false);
        exit.setForeground(Color.BLACK);
        exit.addActionListener(this);
        c.gridx = 1;
        panel.add(exit, c);

        update();
    }

    public void update(){
        Lobby lobby = clientController.getWaitingLobbies().get(lobbyID);
        if (lobby == null) return;

        playersPanel.removeAll();
        for (Player p : lobby.getPlayers().values()) {
            JLabel infoP = new JLabel(p.getName() + " - " + p.getTotem());
            infoP.setFont(Fonts.small);
            playersPanel.add(infoP);
        }

        totemBox.removeAllItems();
        List<Totem> totiPresi = new ArrayList<>();
        for (Player p : lobby.getPlayers().values())
            totiPresi.add(p.getTotem());
        for (Totem t : Totem.values())
            if (!totiPresi.contains(t))
                totemBox.addItem(t);

        playersPanel.revalidate();
        playersPanel.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == join){
            join.setEnabled(false);
            String name = nameText.getText();
            Totem totem = (Totem) totemBox.getSelectedItem();
            try {
                clientController.getServer().joinLobby(clientController.getID(),lobbyID,new Player(name,totem));
                clientController.getServer().getLobbyInfo(clientController.getID(), lobbyID);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            update();
        }else if(e.getSource()==exit){
            join.setEnabled(true);
            try {
                clientController.getServer().leaveLobby(clientController.getID(), lobbyID);
                clientController.getServer().getLobbyInfo(clientController.getID(), lobbyID);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            update();
        }

    }
}

