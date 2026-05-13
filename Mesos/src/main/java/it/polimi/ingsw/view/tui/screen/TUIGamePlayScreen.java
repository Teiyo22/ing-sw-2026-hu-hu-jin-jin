package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.action.*;
import it.polimi.ingsw.view.tui.section.*;

import java.util.List;

public class TUIGamePlayScreen extends TUIScreen {
    public TUIGamePlayScreen(ClientController clientController) {
        super(clientController);

        registry = new ActionRegistry()
                .register(new DisconnectAction(clientController))
                .register(new ShowAction(clientController))
                .register(new HideAction(clientController))
                .register(new PickOfferAction(clientController))
                .register(new PickCardAction(clientController))
                .register(new LeaveLobbyAction(clientController));

        sections = List.of(
                new ClientInfoSection(),
                new ActionSection(registry),
                new PlayerInfoSection(),
                new OrderTileSection(),
                new RowSection(true),
                new OfferTrackSection(),
                new RowSection(false),
                new PlayerFocusSection()
        );
    }


}
