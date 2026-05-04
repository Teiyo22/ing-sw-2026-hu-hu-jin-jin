package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.screen.*;

public enum ScreenType {
    LOBBY_SELECTION,
    GAME_PLAY,
    CARD_PICK,
    OFFER_PICK,
    GAME_END;

    public static Screen getTUIScreen(ScreenType type, ClientController clientController) {
        return switch (type) {
            case LOBBY_SELECTION -> new LobbySelectionScreen(clientController);
            case GAME_PLAY -> new GamePlayScreen(clientController);
            case CARD_PICK -> new CardPickScreen(clientController);
            case OFFER_PICK -> new OfferPickScreen(clientController);
            case GAME_END -> new GameEndScreen(clientController);
            default -> throw new IllegalArgumentException("Unknown screen type: " + type);
        };
    }
}
