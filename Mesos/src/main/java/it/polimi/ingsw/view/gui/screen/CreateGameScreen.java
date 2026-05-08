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

public class CreateGameScreen extends  GUIScreen implements ActionListener {
    private JButton back;
    private JButton create;
    private JTextField nameText;
    private JComboBox<Integer> pSizeBox;
    private JComboBox<Totem> totemBox;

    public CreateGameScreen(JFrame frame, ClientController clientController) {
        super(frame, clientController);
    }

    public void render() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFFF3D3));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0xFFF3D3));

        back = new JButton("←");
        back.setFont(new Font("Arial", Font.PLAIN, 30));
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setForeground(Color.BLACK);
        back.addActionListener(this);
        topBar.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Create game");
        title.setFont(new Font("Arial", Font.PLAIN, 40));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        topBar.add(title, BorderLayout.CENTER);

        panel.add(topBar, BorderLayout.NORTH);

        JPanel createPanel = new JPanel(new GridBagLayout());
        createPanel.setBackground(new Color(0xFFF3D3));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 10, 15, 10);
        c.anchor = GridBagConstraints.WEST;

        JLabel name = new JLabel("Name :");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 0; c.gridy = 1; c.anchor = GridBagConstraints.EAST;
        createPanel.add(name,c);

        nameText = new JTextField();
        nameText.addActionListener(this);
        nameText.setBackground(Color.WHITE);
        nameText.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.gridy = 1; c.anchor = GridBagConstraints.WEST;
        createPanel.add(nameText,c);

        JLabel size = new JLabel("Players number:");
        size.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 0; c.gridy = 2;
        createPanel.add(size, c);

        Integer[] pSize = {2,3,4,5};
        pSizeBox = new JComboBox<>(pSize);
        pSizeBox.setPreferredSize(new Dimension(200,30));
        pSizeBox.setFont(new Font("Arial", Font.PLAIN, 18));
        c.gridx = 1; c.gridy = 2;
        createPanel.add(pSizeBox, c);

        JLabel totemLabel = new JLabel("Scegli totem:");
        totemLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 0; c.gridy = 3; c.anchor = GridBagConstraints.EAST;
        createPanel.add(totemLabel, c);

        totemBox = new JComboBox<>(Totem.values());
        totemBox.setFont(new Font("Arial", Font.PLAIN, 18));
        totemBox.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.gridy = 3; c.anchor = GridBagConstraints.EAST;
        createPanel.add(totemBox, c);

        create = new JButton("Crea");
        create.setFont(new Font("Arial", Font.PLAIN, 30));
        create.setBorderPainted(false);
        create.setContentAreaFilled(false);
        create.setFocusable(false);
        create.setForeground(Color.BLACK);
        create.addActionListener(this);
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        createPanel.add(create, c);

        panel.add(createPanel, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setVisible(true);
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
            new MenuScreen(frame, clientController);
        }
    }
}
