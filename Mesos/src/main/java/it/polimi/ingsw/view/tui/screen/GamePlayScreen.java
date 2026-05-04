package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.Screen;

public class GamePlayScreen implements Screen {
    private final ClientController clientController;

    public GamePlayScreen(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void render() {

    }

    @Override
    public void handleInput(String input) {

    }

    @Override
    public void update() {

    }

    @Override
    public void onExit() {

    }
}
