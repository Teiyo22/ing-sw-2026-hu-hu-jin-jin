package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;

import javax.swing.*;

public class GUIView implements View {
    private ClientController controller;

    public GUIView(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void show() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(true);

        //TODO: create first screen and call render()
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
