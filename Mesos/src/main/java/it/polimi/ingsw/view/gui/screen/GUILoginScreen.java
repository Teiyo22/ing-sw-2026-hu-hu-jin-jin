package it.polimi.ingsw.view.gui.screen;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.action.GUILoginAction;
import it.polimi.ingsw.view.gui.util.Fonts;
import it.polimi.ingsw.view.gui.util.PanelBuilder;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public class GUILoginScreen extends GUIScreen{
    private JPanel panel1;
    private JPanel loginpanel;

    public GUILoginScreen(GUIView frame, ClientController clientController) {
        super(frame,clientController);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(250,0,0,0);
        panel1 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon(getClass().getResource("/images/mesos.png"));
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        JLabel name = WidgetFactory.createLabel("Username");
        name.setMaximumSize(new Dimension(250, 35));

        JTextField username = WidgetFactory.createTextField();
        username.setMaximumSize(new Dimension(250, 30));

        JButton login = WidgetFactory.createButton(new GUILoginAction(clientController, username));
        login.setMaximumSize(new Dimension(250, 35));
        login.setText("Login");

        loginpanel = new PanelBuilder().rounded(40,5, Fonts.select,name,username,login)
                .centered()
                .buildPanel();
        loginpanel.setPreferredSize(new Dimension(350, 200));

        panel1.add(loginpanel,c);
    }

    @Override
    public void render() {
        frame.setContentPane(panel1);
        frame.setVisible(true);
    }

    @Override
    public void showError(String error) {

    }
}



