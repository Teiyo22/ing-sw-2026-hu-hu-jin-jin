package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.gui.GUIView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUIScreen extends JPanel implements ActionListener, Screen {
    protected GUIView frame;
    protected ClientController clientController;

    public GUIScreen(GUIView frame, ClientController clientController) {
        this.frame = frame;
        this.clientController = clientController;
    }

    public abstract void render();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon bg = new ImageIcon(getClass().getResource("/images/mesosBlurred.png"));
        g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e){}
}
