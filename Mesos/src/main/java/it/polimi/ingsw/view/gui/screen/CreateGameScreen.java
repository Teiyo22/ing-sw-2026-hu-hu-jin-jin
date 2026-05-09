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

public class CreateGameScreen extends  GUIScreen implements ActionListener {
    private JButton back;
    private JButton create;
    private JTextField nameText;
    private JComboBox<Integer> pSizeBox;
    private JComboBox<Totem> totemBox;
    private JPanel panel;
    private JButton previous;

    public CreateGameScreen(JFrame frame,JButton previous, ClientController clientController, JPanel panel) {
        super(frame, clientController);
        this.panel = panel;
        this.previous = previous;
    }

    public void render() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Fonts.black);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Fonts.black);

        back = new JButton("←");
        back.setFont(Fonts.medium);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        topBar.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Create game");
        title.setFont(Fonts.large);
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        topBar.add(title, BorderLayout.CENTER);

        panel.add(topBar, BorderLayout.NORTH);

        JPanel createPanel = new JPanel(new GridBagLayout());
        createPanel.setBackground(Fonts.black);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 10, 15, 10);
        c.anchor = GridBagConstraints.WEST;

        JLabel name = new JLabel("Name :");
        name.setFont(Fonts.small);
        name.setForeground(Color.WHITE);
        c.gridx = 0; c.gridy = 1; c.anchor = GridBagConstraints.EAST;
        createPanel.add(name,c);

        nameText = new JTextField();
        nameText.addActionListener(this);
        nameText.setBackground(Color.WHITE);
        nameText.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.gridy = 1; c.anchor = GridBagConstraints.WEST;
        createPanel.add(nameText,c);

        JLabel size = new JLabel("Players number:");
        size.setFont(Fonts.small);
        size.setForeground(Color.WHITE);
        c.gridx = 0; c.gridy = 2;
        createPanel.add(size, c);

        Integer[] pSize = {2,3,4,5};
        pSizeBox = new JComboBox<>(pSize);
        pSizeBox.setPreferredSize(new Dimension(200,30));
        pSizeBox.setFont(Fonts.small);
        c.gridx = 1; c.gridy = 2;
        createPanel.add(pSizeBox, c);

        JLabel totemLabel = new JLabel("Totem:");
        totemLabel.setFont(Fonts.small);
        totemLabel.setForeground(Color.WHITE);
        c.gridx = 0; c.gridy = 3; c.anchor = GridBagConstraints.EAST;
        createPanel.add(totemLabel, c);

        totemBox = new JComboBox<>(Totem.values());
        totemBox.setFont(Fonts.small);
        totemBox.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.gridy = 3; c.anchor = GridBagConstraints.EAST;
        createPanel.add(totemBox, c);

        create = new JButton("Create");
        create.setFont(Fonts.medium);
        create.setBorderPainted(false);
        create.setContentAreaFilled(false);
        create.setFocusable(false);
        create.setForeground(Color.WHITE);
        create.addActionListener(this);
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        createPanel.add(create, c);

        panel.add(createPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == create){
            String name = nameText.getText();
            Totem totem = (Totem) totemBox.getSelectedItem();
            int size = (int) pSizeBox.getSelectedItem();
            try {
                clientController.getServer().createLobby(clientController.getID(), size, new Player(name, totem));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }else if(e.getSource() == back){
            panel.removeAll();
            panel.setLayout(new GridBagLayout());
            panel.add(previous);
            panel.revalidate();
            panel.repaint();
        }
    }
}
