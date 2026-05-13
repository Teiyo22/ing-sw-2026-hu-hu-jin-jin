package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.screen.GUIMenuScreen;
import it.polimi.ingsw.view.gui.screen.GUIScreen;
import it.polimi.ingsw.view.gui.screen.GUIGamePlayScreen;
import it.polimi.ingsw.view.gui.screen.GUILobbySelectionScreen;
import it.polimi.ingsw.view.tui.screen.*;

import javax.swing.*;

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

    public static GUIScreen getGUIScreen(ScreenType type, GUIView frame, ClientController clientController) {
        return switch(type) {
            case LOGIN -> new GUIMenuScreen(frame, clientController);
            case LOBBY_SELECTION -> new GUILobbySelectionScreen(frame, clientController);
            case GAME_PLAY -> new GUIGamePlayScreen(frame, clientController);
        };
    }
}
