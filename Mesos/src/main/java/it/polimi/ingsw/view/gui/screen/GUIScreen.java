package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUIScreen implements ActionListener {
    protected JFrame frame;
    protected ClientController controller;

    public GUIScreen(JFrame frame, ClientController controller) {
        this.frame = frame;
        this.controller = controller;
    }

    public abstract void render();

    @Override
    public void actionPerformed(ActionEvent e){}
}
