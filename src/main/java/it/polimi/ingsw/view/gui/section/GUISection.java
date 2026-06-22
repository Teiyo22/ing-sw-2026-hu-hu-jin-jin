package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;

import javax.swing.*;

public abstract class GUISection {
    protected JPanel panel;

    public abstract void render(ClientController controller);

    public JPanel getPanel() {
        return panel;
    }
}
