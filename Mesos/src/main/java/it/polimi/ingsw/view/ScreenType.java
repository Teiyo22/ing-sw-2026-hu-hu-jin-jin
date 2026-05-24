package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.screen.*;
import it.polimi.ingsw.view.tui.screen.*;

public enum ScreenType {
    LOGIN,
    LOBBY_SELECTION,
    GAME_PLAY,
    GAME_END,
    LEADERBOARD;

    public static TUIScreen getTUIScreen(ScreenType type, ClientController clientController) {
        return switch (type) {
            case LOGIN -> new TUILoginScreen(clientController);
            case LOBBY_SELECTION -> new TUILobbySelectionScreen(clientController);
            case GAME_PLAY -> new TUIGamePlayScreen(clientController);
            case GAME_END -> null;
            case LEADERBOARD -> null;
        };
    }

    public static GUIScreen getGUIScreen(ScreenType type, GUIView frame, ClientController clientController) {
        return switch(type) {
            case LOGIN -> new GUILoginScreen(frame, clientController);
            case LOBBY_SELECTION -> new GUILobbySelectionScreen(frame, clientController);
            case GAME_PLAY -> new GUIGamePlayScreen(frame, clientController);
            case GAME_END -> new GUIGameEndScreen(frame, clientController);
            case LEADERBOARD -> new GUILeaderboardScreen(frame, clientController);
        };
    }
}
