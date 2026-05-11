package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.tui.screen.TUIScreen;

import java.io.IOError;
import java.util.Scanner;

public class TUIView implements View {
    private TUIScreen currScreen;
    private final ClientController clientController;

    private boolean running = true;

    public TUIView(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void show() {
        transitionTo(ScreenType.LOBBY_SELECTION);
        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                currScreen.render();
                String input = scanner.nextLine().trim();
                currScreen.handleInput(input);
            }
        } catch (IOError e) {
            clientController.disconnect();
        }
    }

    @Override
    public void close() {
        running = false;
    }

    @Override
    public void displayError(String message) {
        currScreen.showError(message);
    }

    @Override
    public void update() {
        if (running)
            currScreen.render();
    }

    @Override
    public void transitionTo(ScreenType type) {
        currScreen = ScreenType.getTUIScreen(type, clientController);
        currScreen.render();
    }
}
