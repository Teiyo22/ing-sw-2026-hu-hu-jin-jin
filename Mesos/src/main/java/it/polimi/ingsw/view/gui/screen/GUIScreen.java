package it.polimi.ingsw.view.gui.screen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GUIScreen implements ActionListener {
    JFrame frame;

    public GUIScreen(JFrame frame) {
        this.frame = frame;
    }

    public abstract void render();

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
