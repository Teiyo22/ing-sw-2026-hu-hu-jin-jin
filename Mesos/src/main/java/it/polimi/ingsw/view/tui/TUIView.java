package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;

import java.util.Scanner;

public class TUIView implements View {
    private Screen currScreen;
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
                String input = scanner.nextLine().trim();
                currScreen.handleInput(input);
            }
        }
    }

    @Override
    public void close() {
        running = false;
    }

    @Override
    public void displayError(String message) {

    }

    @Override
    public void update() {
        currScreen.update();
        currScreen.render();
    }

    @Override
    public void transitionTo(ScreenType type) {
        currScreen = ScreenType.getTUIScreen(type, clientController);
        currScreen.update();
        currScreen.render();
    }
}
