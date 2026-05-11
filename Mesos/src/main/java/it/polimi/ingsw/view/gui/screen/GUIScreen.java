package it.polimi.ingsw.view.gui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.gui.GUIView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUIScreen implements ActionListener, Screen {
    protected GUIView frame;
    protected ClientController clientController;

    public GUIScreen(GUIView frame, ClientController clientController) {
        this.frame = frame;
        this.clientController = clientController;
    }

    public abstract void render();

    @Override
    public void actionPerformed(ActionEvent e){}
}
