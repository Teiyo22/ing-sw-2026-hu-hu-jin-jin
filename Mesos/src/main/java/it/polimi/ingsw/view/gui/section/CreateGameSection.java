package it.polimi.ingsw.view.gui.section;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.CreateLobbyCommand;
import it.polimi.ingsw.view.gui.util.Fonts;

import javax.swing.*;
import java.awt.*;

public class CreateGameSection implements GUISection {

    @Override
    public void render(ClientController clientController, JPanel container) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Fonts.black);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Fonts.black);

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

        JTextField nameText = new JTextField();
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
        JComboBox<Integer> pSizeBox = new JComboBox<>(pSize);
        pSizeBox.setPreferredSize(new Dimension(200,30));
        pSizeBox.setFont(Fonts.small);
        c.gridx = 1; c.gridy = 2;
        createPanel.add(pSizeBox, c);

        JLabel totemLabel = new JLabel("Totem:");
        totemLabel.setFont(Fonts.small);
        totemLabel.setForeground(Color.WHITE);
        c.gridx = 0; c.gridy = 3; c.anchor = GridBagConstraints.EAST;
        createPanel.add(totemLabel, c);

        JComboBox<Totem> totemBox = new JComboBox<>(Totem.values());
        totemBox.setFont(Fonts.small);
        totemBox.setPreferredSize(new Dimension(200,30));
        c.gridx = 1; c.gridy = 3; c.anchor = GridBagConstraints.EAST;
        createPanel.add(totemBox, c);

        JButton create = new JButton("Create");
        create.setFont(Fonts.medium);
        create.setBorderPainted(false);
        create.setContentAreaFilled(false);
        create.setFocusable(false);
        create.setForeground(Color.WHITE);
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2; c.anchor = GridBagConstraints.CENTER;
        createPanel.add(create, c);
        create.addActionListener(e -> {
            if (nameText.getText().isBlank()) return;
            new CreateLobbyCommand((int) pSizeBox.getSelectedItem(),
                    new Player(nameText.getText(), (Totem) totemBox.getSelectedItem())).execute(clientController);
        });
        panel.add(createPanel, BorderLayout.CENTER);

        container.add(panel, BorderLayout.SOUTH);
    }

    private boolean visible;

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    @Override
    public boolean isVisible(ClientController controller) {
        return visible;
    }
}
