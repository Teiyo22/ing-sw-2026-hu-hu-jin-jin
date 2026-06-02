package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.action.*;
import it.polimi.ingsw.view.tui.section.*;

import java.util.ArrayList;
import java.util.List;

public class TUIGamePlayScreen extends TUIScreen {
    public TUIGamePlayScreen(ClientController clientController) {
        super(clientController);

        eventResults = new ArrayList<>();

        registry = new ActionRegistry()
            .register(new TUIDisconnectAction(clientController))
            .register(new TUIShowAction(clientController))
            .register(new TUIHideAction(clientController))
            .register(new TUIPickOfferAction(clientController))
            .register(new TUIPickCardAction(clientController))
            .register(new TUILeaveLobbyAction(clientController))
            .register(new TUIShowEndAction(clientController));

        sections = List.of(
            new TUIClientInfoSection(),
            new TUIActionSection(registry),
            new TUIPlayerInfoSection(),
            new TUIOrderTileSection(),
            new TUIRowSection(true),
            new TUIOfferTrackSection(),
            new TUIRowSection(false),
            new TUIPlayerFocusSection()
        );
    }
}
