package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.screen.GUIScreen;
import it.polimi.ingsw.view.gui.screen.GUIMenuScreen;

import javax.swing.*;

public class GUIView implements View {
    private final ClientController controller;
    private JFrame frame;
    private GUIScreen currScreen;

    public GUIView(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void show() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(true);

        currScreen = new GUIMenuScreen(frame, controller);
        currScreen.render();
    }

    @Override
    public void close() {
        frame.dispose();
        JOptionPane.showMessageDialog(frame, "Disconnected from server", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void displayError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void transitionTo(ScreenType type) {
        SwingUtilities.invokeLater(() -> {
            currScreen = ScreenType.getGUIScreen(type, frame, controller);
            currScreen.render();
        });
    }


    @Override
    public void update() {
        if (frame.isDisplayable()) {
            SwingUtilities.invokeLater(() -> currScreen.render());
        }
    }
}
