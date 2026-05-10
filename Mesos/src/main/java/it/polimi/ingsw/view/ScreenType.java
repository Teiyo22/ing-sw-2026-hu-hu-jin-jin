package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.screen.GUIScreen;
import it.polimi.ingsw.view.tui.screen.*;

import javax.swing.*;

public enum ScreenType {
    LOBBY_SELECTION,
    GAME_PLAY;

    public static TUIScreen getTUIScreen(ScreenType type, ClientController clientController) {
        return switch (type) {
            case LOBBY_SELECTION -> new TUILobbySelectionScreen(clientController);
            case GAME_PLAY -> new TUIGamePlayScreen(clientController);
        };
    }

    public static GUIScreen getGUIScreen(ScreenType type, JFrame frame, ClientController clientController) {
        return switch(type) {
            case GAME_PLAY -> new it.polimi.ingsw.view.gui.screen.GamePlayScreen(frame, clientController);
        };
    }
}
