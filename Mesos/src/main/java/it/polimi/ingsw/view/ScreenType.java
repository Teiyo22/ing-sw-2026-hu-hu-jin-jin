package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.screen.*;

public enum ScreenType {
    LOBBY_SELECTION,
    GAME_PLAY;

    public static Screen getTUIScreen(ScreenType type, ClientController clientController) {
        return switch (type) {
            case LOBBY_SELECTION -> new LobbySelectionScreen(clientController);
            case GAME_PLAY -> new GamePlayScreen(clientController);
        };
    }
}
