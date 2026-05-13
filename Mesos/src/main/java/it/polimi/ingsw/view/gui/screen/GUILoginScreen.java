package it.polimi.ingsw.view.gui.screen;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.action.GUILoginAction;
import it.polimi.ingsw.view.gui.util.factory.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public class GUILoginScreen extends GUIScreen{
    private JPanel panel1;

    public GUILoginScreen(GUIView frame, ClientController clientController) {
        super(frame,clientController);
        panel1 = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon(getClass().getResource("/images/mesos.png"));
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(400,0,0,0);
        c.anchor = GridBagConstraints.EAST; c.gridx = 0;
        JLabel name = WidgetFactory.createLabel("Insert username : ");
        JTextField login = WidgetFactory.createTextField();
        panel1.add(name,c);
        c.gridx = 1;
        panel1.add(login,c);
        login.addActionListener(new GUILoginAction(clientController, login));
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



