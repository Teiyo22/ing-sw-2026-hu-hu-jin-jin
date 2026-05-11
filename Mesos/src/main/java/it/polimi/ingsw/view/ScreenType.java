package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.screen.*;

public enum ScreenType {
    LOBBY_SELECTION,
    GAME_PLAY;

    public static TUIScreen getTUIScreen(ScreenType type, ClientController clientController) {
        return switch (type) {
            case LOBBY_SELECTION -> new TUILobbySelectionScreen(clientController);
            case GAME_PLAY -> new TUIGamePlayScreen(clientController);
        };
    }
}
