package it.polimi.ingsw.view.gui.section;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.gui.action.GUICreateLobbyAction;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateGameSection implements GUISection {
    private JPanel panel;
    private JPanel playerInput;
    private final JComboBox<Integer> playerNumBox;
    private final JComboBox<Totem> totemBox;
    private final JButton create;
    private final JButton back;

    public CreateGameSection(ClientController clientController, JPanel placeholder) {
        JLabel title = new JLabel("Create game");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(Fonts.large);
        title.setForeground(Color.WHITE);

        JLabel playerNumLabel = WidgetFactory.createLabel("Player Number:");
        playerNumBox = WidgetFactory.createBox(new Integer[]{2, 3, 4, 5});
        playerNumBox.setMaximumSize(new Dimension(200,30));
        JLabel totemLabel = WidgetFactory.createLabel("Totem:");
        totemBox = WidgetFactory.createBox(Totem.values());
        totemBox.setMaximumSize(new Dimension(200,30));

        playerInput = new PanelBuilder()
                .rounded(20, 5 ,new Color(Fonts.menu2.getRed(),Fonts.menu2.getGreen(),Fonts.menu2.getBlue(),120), playerNumLabel,playerNumBox,totemLabel,totemBox)
                .centered()
                .buildPanel();

        create = WidgetFactory.createButton(new GUICreateLobbyAction(clientController, playerNumBox, totemBox));

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);

        panel = new PanelBuilder()
            .column(10, titleBar, playerInput, create)
            .withColor(Fonts.weird_purple)
            .withPadding(20, 20, 20, 20 )
            .buildPanel();
        panel.setPreferredSize(new Dimension(400, 300));

        back = WidgetFactory.createButton(new AbstractAction("←") {
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) placeholder.getLayout()).show(placeholder, "button");
            }
        });
        titleBar.add(back, BorderLayout.WEST);
        titleBar.add(title, BorderLayout.CENTER);
        titleBar.add(Box.createHorizontalStrut(back.getPreferredSize().width), BorderLayout.EAST);
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
