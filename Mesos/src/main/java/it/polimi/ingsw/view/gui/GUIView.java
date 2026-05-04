package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;

public class GUIView implements View {
    private ClientController controller;

    public GUIView(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void show() {

    }

    @Override
    public void close() {

    }

    @Override
    public void update() {

    }

    @Override
    public void displayError(String message) {

    }

    @Override
    public void transitionTo(ScreenType type) {

    }
}
