package it.polimi.ingsw.view.gui.section;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.action.GUICreateLobbyAction;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public class CreateGameSection implements GUISection {
    private final JPanel panel;
    private final JButton createBtn;
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

        JPanel formPanel = createFormPanel(clientController);
        createBtn = WidgetFactory.createButton("Create Game", () -> cardLayout.show(panel, "form"));

        panel.setOpaque(false);
        panel.add(createBtn, "button");
        panel.add(formPanel, "form");
        cardLayout.show(panel, "button");

    }

    @Override
    public void render(ClientController clientController) {
        Lobby currLobby = clientController.getCurrLobby();

        if (currLobby != null && currLobby.containsClient(clientController.getID())) {
            createBtn.setEnabled(false);
            cardLayout.show(panel, "button");
        } else {
            createBtn.setEnabled(true);
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    public JPanel createFormPanel(ClientController clientController) {
        // Title bar creation
        JButton back = WidgetFactory.createButton("←", () -> cardLayout.show(panel, "button"));
        JLabel title = WidgetFactory.createLabel("Create Game");
        JPanel placeholder = new PanelBuilder().size(back.getPreferredSize()).buildPanel();

        JPanel titleBar = new PanelBuilder()
                .border(null, title, null, back, placeholder)
                .buildPanel();

        // Player input creation
        JLabel playerNumLabel = WidgetFactory.createLabel("Player Number: ");
        JComboBox<Integer> playerNumBox = WidgetFactory.createBox(new Integer[]{2, 3, 4, 5}, 200, 20);

        JLabel totemLabel = WidgetFactory.createLabel("Totem: ");
        JComboBox<Totem> totemBox = WidgetFactory.createBox(Totem.values(), 200, 20);

        JPanel playerInput = new PanelBuilder()
                .rounded(20)
                .grid(2, 10, 10, playerNumLabel, playerNumBox, totemLabel, totemBox)
                .withPadding(20, 20, 20, 20)
                .withTranslucentColor(Fonts.select)
                .buildPanel();

        JButton create = WidgetFactory.createButton(new GUICreateLobbyAction(clientController, playerNumBox, totemBox));

        return new PanelBuilder()
                .column(10, titleBar, playerInput, create)
                .buildPanel();
    }
}
