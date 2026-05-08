package it.polimi.ingsw.view.gui.screen;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.Lobby;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;

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

    public LobbyInfoScreen(JFrame frame, ClientController clientController, JPanel panel, int lobbyID) {
        super(frame, clientController);
        this.panel = panel;
        this.lobbyID = lobbyID;
    }

    public void render() {
        Lobby lobby = clientController.getCurrLobby();
        Map<Integer, Player> players = lobby.getPlayers();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel(String.valueOf(lobbyID));
        title.setFont(new Font("Arial", Font.BOLD, 28));
        c.gridy = 0;
        panel.add(title, c);

        for(Player p : players.values()) {
            JLabel infoP = new JLabel(p.getName() + "-" + p.getTotem());
            infoP.setFont(new Font("Arial", Font.PLAIN, 18));
            c.gridy++;
            panel.add(infoP, c);
        }

        JLabel name = new JLabel("Name :");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 0; c.gridy++; c.anchor = GridBagConstraints.EAST;
        panel.add(name,c);

        nameText = new JTextField();
        nameText.addActionListener(this);
        nameText.setBackground(Color.WHITE);
        nameText.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        panel.add(nameText,c);

        JLabel totemLabel = new JLabel("Scegli totem:");
        totemLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 0; c.gridy++; c.anchor = GridBagConstraints.EAST;
        panel.add(totemLabel, c);

        List<Totem> totiPresi = new ArrayList<>();
        for(Player p : players.values())
            totiPresi.add(p.getTotem());
        for(Totem t : Totem.values())
            if(!totiPresi.contains(t))
                totemBox.addItem(t);

        totemBox.setFont(new Font("Arial", Font.PLAIN, 18));
        totemBox.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        panel.add(totemBox, c);


        join = new JButton("Join");
        join.setFont(new Font("Arial",Font.PLAIN,30));
        join.setBorderPainted(false);
        join.setContentAreaFilled(false);
        join.setFocusable(false);
        join.setForeground(Color.BLACK);
        join.addActionListener(this);
        c.gridy++;
        panel.add(join, c);

        exit = new JButton("Exit");
        exit.setFont(new Font("Arial",Font.PLAIN,30));
        exit.setBorderPainted(false);
        exit.setContentAreaFilled(false);
        exit.setFocusable(false);
        exit.setForeground(Color.BLACK);
        exit.addActionListener(this);
        c.gridx = 1;
        panel.add(exit, c);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == join){
            join.setEnabled(false);
            String name = nameText.getText();
            Totem totem = (Totem) totemBox.getSelectedItem();
            try {
                clientController.getServer().joinLobby(clientController.getID(),lobbyID,new Player(name,totem));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            panel.removeAll();
            render();
            panel.revalidate();
            panel.repaint();
        }else if(e.getSource()==exit){
            join.setEnabled(true);
            try {
                clientController.getServer().leaveLobby(clientController.getID(), lobbyID);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            panel.removeAll();
            render();
            panel.revalidate();
            panel.repaint();
        }

    }
}

