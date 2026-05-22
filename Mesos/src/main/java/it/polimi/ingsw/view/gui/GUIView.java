package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.screen.GUIScreen;
import it.polimi.ingsw.view.gui.screen.GUILoginScreen;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIView extends JFrame implements View {
    private final ClientController controller;
    private GUIScreen currScreen;

    public GUIView(ClientController controller) {
        super();
        this.controller = controller;

        setTitle("Mesos");
        setSize(1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.disconnect();
                dispose();
            }
        });
    }

    @Override
    public void start() {
        currScreen = new GUILoginScreen(this, controller);
        currScreen.render();

        setVisible(true);
    }

    @Override
    public void close() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Disconnected from server", "Error", JOptionPane.ERROR_MESSAGE);
        });

        dispose();
    }

    @Override
    public void displayError(String message) {
        currScreen.showError(message);
    }

    @Override
    public void transitionTo(ScreenType type) {
        SwingUtilities.invokeLater(() -> {
            currScreen = ScreenType.getGUIScreen(type, this, controller);
            currScreen.render();
        });
    }


    @Override
    public void notifyChange() {
        if (isDisplayable()) {
            SwingUtilities.invokeLater(() -> currScreen.render());
        }
    }
}
