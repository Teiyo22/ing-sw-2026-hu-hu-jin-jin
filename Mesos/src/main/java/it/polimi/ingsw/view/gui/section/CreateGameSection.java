package it.polimi.ingsw.view.gui.section;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.action.GUICreateLobbyAction;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class CreateGameSection implements GUISection {
    private JPanel panel;
    private JPanel playerInput;
    private final JComboBox<Integer> playerNumBox;
    private final JComboBox<Totem> totemBox;
    private final JButton create;
    private final JButton back;
    private final CardLayout cardLayout;

    public CreateGameSection(ClientController clientController) {
        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout) {
            @Override
            public Dimension getPreferredSize() {
                for (Component c : getComponents()) {
                    if (c.isVisible()) {
                        return c.getPreferredSize();
                    }
                }
                return super.getPreferredSize();
            }
        };

        JLabel title = new JLabel("Create game");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(Fonts.medium);
        title.setForeground(Color.WHITE);

        JLabel playerNumLabel = WidgetFactory.createLabel("Player Number:");
        playerNumBox = WidgetFactory.createBox(new Integer[]{2, 3, 4, 5});
        playerNumBox.setMaximumSize(new Dimension(200,20));

        JLabel totemLabel = WidgetFactory.createLabel("Totem:");
        totemBox = WidgetFactory.createBox(Totem.values());
        totemBox.setMaximumSize(new Dimension(200,20));

        playerInput = new PanelBuilder()
                .rounded(20, 5 ,Fonts.select, playerNumLabel,playerNumBox,totemLabel,totemBox)
                .centered()
                .buildPanel();

        create = WidgetFactory.createButton(new GUICreateLobbyAction(clientController, playerNumBox, totemBox));

        back = WidgetFactory.createButton(new AbstractAction("←") {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "button");
            }
        });

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);
        titleBar.add(back, BorderLayout.WEST);
        titleBar.add(title, BorderLayout.CENTER);
        titleBar.add(Box.createHorizontalStrut(back.getPreferredSize().width), BorderLayout.EAST);

        JPanel formPanel = new PanelBuilder()
                .column(10, titleBar, playerInput, create)
                .buildPanel();

        JButton createBtn = WidgetFactory.createButton(new AbstractAction("Create Lobby") {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panel, "form");
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(createBtn);

        panel.setOpaque(false);
        panel.add(buttonPanel, "button");
        panel.add(formPanel, "form");
        cardLayout.show(panel, "button");

    }

    @Override
    public void render(ClientController clientController, JPanel container) {
        boolean visible = isVisible(clientController);
        panel.setVisible(visible);

        if (visible) {
            cardLayout.show(panel, "button");

            panel.revalidate();
            panel.repaint();
        }

    }
    @Override
    public boolean isVisible(ClientController clientController) {
        if (clientController.getCurrLobby() == null) {return true;}

        Map<Player, Boolean> players = clientController.getCurrLobby().getPlayers();
        for (Player p : players.keySet()) {
            if (p.getName().equals(clientController.getID())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
