package it.polimi.ingsw.view.gui.section;

import it.polimi.ingsw.controller.client.ClientController;

import javax.swing.*;

public interface GUISection {
    void render(ClientController controller);
    boolean isVisible(ClientController controller);
    JPanel getPanel();
}
