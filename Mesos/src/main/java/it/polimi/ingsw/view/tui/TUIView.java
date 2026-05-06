package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;
import it.polimi.ingsw.view.ScreenType;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
                if (System.in.available() > 0) {   // controlla prima
                    String input = scanner.nextLine().trim();
                    currScreen.handleInput(input);
                }

                TimeUnit.MILLISECONDS.sleep(50);
            }
        } catch (IOException | InterruptedException ignore) { }
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
