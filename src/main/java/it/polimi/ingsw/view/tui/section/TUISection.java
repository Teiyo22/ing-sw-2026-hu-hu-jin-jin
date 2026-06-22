package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;

public interface TUISection {
    void render(ClientController clientController);
    boolean isVisible(ClientController clientController);
}