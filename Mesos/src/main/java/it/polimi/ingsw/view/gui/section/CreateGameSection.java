package it.polimi.ingsw.view.gui.section;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.CreateLobbyCommand;
import it.polimi.ingsw.view.gui.action.GUICreateLobbyAction;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public class CreateGameSection implements GUISection {
    private JPanel panel;
    private final JTextField nameText;
    private final JComboBox<Integer> playerNumBox;
    private final JComboBox<Totem> totemBox;
    private final JButton create_btn;
    private final JPanel playerInputGrid;

    public CreateGameSection(ClientController clientController) {
        JLabel title = new JLabel("Create game");
        title.setFont(Fonts.large);
        title.setForeground(Color.WHITE);

        JLabel nameLabel = WidgetFactory.createLabel("Name:");
        nameText = WidgetFactory.createTextField();
        JLabel playerNumLabel = WidgetFactory.createLabel("Player Number:");
        playerNumBox = WidgetFactory.createBox(new Integer[]{2, 3, 4, 5});
        JLabel totemLabel = WidgetFactory.createLabel("Totem:");
        totemBox = WidgetFactory.createBox(Totem.values());

        playerInputGrid = new PanelBuilder()
            .grid(2, 5, 5, nameLabel, nameText, playerNumLabel, playerNumBox, totemLabel, totemBox)
            .withColor(Fonts.red_purple)
            .withPadding(10, 10, 10, 10)
            .buildPanel();

            create_btn = WidgetFactory.createButton(new GUICreateLobbyAction(clientController, playerNumBox, nameText, totemBox));

        panel = new PanelBuilder()
            .column(10, title, playerInputGrid, create_btn)
            .withColor(Fonts.weird_purple)
            .withPadding(100, 50, 100, 50)
            .buildPanel();
    }

    @Override
    public void render(ClientController clientController, JPanel container) {

    }

    private boolean visible;

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    @Override
    public boolean isVisible(ClientController controller) {
        return visible;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
