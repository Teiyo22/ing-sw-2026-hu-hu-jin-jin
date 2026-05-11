package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.screen.GUIScreen;
import it.polimi.ingsw.view.gui.screen.GUIMenuScreen;

import javax.swing.*;

public class GUIView extends JFrame implements View {
    private final ClientController controller;
    private GUIScreen currScreen;

    public GUIView(ClientController controller) {
        super();
        this.controller = controller;
    }

    @Override
    public void start() {
        setTitle("Mesos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setResizable(true);

        currScreen = new GUIMenuScreen(this, controller);
        currScreen.render();
    }

    @Override
    public void close() {
        dispose();
        JOptionPane.showMessageDialog(this, "Disconnected from server", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void transitionTo(ScreenType type) {
        SwingUtilities.invokeLater(() -> {
            currScreen = ScreenType.getGUIScreen(type, this, controller);
            currScreen.render();
        });
    }


    @Override
    public void update() {
        if (isDisplayable()) {
            SwingUtilities.invokeLater(() -> currScreen.render());
        }
    }
}
