package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;

public interface View {
    void show();
    void close();
    void displayError(String message);
    void transitionTo(ScreenType type);
    ClientController getClientController();
}
