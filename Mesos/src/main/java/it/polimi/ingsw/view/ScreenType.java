package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.screen.*;

public enum ScreenType {
    LOGIN,
    LOBBY_SELECTION,
    GAME_PLAY;

    public static TUIScreen getTUIScreen(ScreenType type, ClientController clientController) {
        return switch (type) {
            case LOGIN -> new TUILoginScreen(clientController);
            case LOBBY_SELECTION -> new TUILobbySelectionScreen(clientController);
            case GAME_PLAY -> new TUIGamePlayScreen(clientController);
        };
    }
}
