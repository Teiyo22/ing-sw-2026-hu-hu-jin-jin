package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.action.GUILoginAction;
import it.polimi.ingsw.utils.view.Fonts;
import it.polimi.ingsw.utils.view.ImageCache;
import it.polimi.ingsw.utils.view.PanelBuilder;
import it.polimi.ingsw.utils.view.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public class GUILoginScreen extends GUIScreen {

    public GUILoginScreen(GUIView frame, ClientController clientController) {
        super(frame, clientController);
        this.background = ImageCache.loadImage("/images/mesos.png");
        this.setLayout(new GridBagLayout());

        JLabel usernameLabel = WidgetFactory.mediumLabel("Username", 250, 35);
        JTextField usernameTextField = WidgetFactory.textField(250, 30);
        JButton login = WidgetFactory.mediumButton(new GUILoginAction(clientController, usernameTextField), 250, 35);

        JPanel loginPanel = new PanelBuilder().rounded(40)
                .column(10, usernameLabel, usernameTextField, login)
                .withTranslucentColor(Fonts.select)
                .centered()
                .withPadding(10, 10, 10, 10)
                .size(350, 200)
                .buildPanel();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(250, 0, 0, 0);
        this.add(loginPanel, c);
    }
}



