package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.common.messages.responses.ErrorMessage;
import it.polimi.ingsw.controller.common.messages.responses.EventResultMessage;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.screen.*;

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
            }
        });
    }

    @Override
    public void start() {
        transitionTo(ScreenType.LOGIN);
        setVisible(true);
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void displayError(ErrorMessage errorMsg) {
        currScreen.showErrors(errorMsg);
    }

    @Override
    public void displayEventResult(EventResultMessage eventResultMessage) {
        currScreen.showEventResult(eventResultMessage);
    }

    @Override
    public void transitionTo(ScreenType type) {
        currScreen = getGUIScreen(type, this, controller);
        this.setContentPane(currScreen);
        this.revalidate();
        notifyChange();
    }

    @Override
    public void notifyChange() {
        if (isDisplayable()) {
            SwingUtilities.invokeLater(() -> {
                currScreen.render();
                this.revalidate();
                this.repaint();
            });
        }
    }

    public GUIScreen getGUIScreen(ScreenType type, GUIView frame, ClientController clientController) {
        return switch(type) {
            case LOGIN -> new GUILoginScreen(frame, clientController);
            case LOBBY_SELECTION -> new GUILobbySelectionScreen(frame, clientController);
            case GAME_PLAY -> new GUIGamePlayScreen(frame, clientController);
            case GAME_END -> new GUIGameEndScreen(frame, clientController);
        };
    }
}
