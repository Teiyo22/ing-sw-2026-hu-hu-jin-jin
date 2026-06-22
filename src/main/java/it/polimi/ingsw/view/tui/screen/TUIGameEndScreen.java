package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.action.*;
import it.polimi.ingsw.view.tui.section.TUIActionSection;
import it.polimi.ingsw.view.tui.section.TUIClientInfoSection;
import it.polimi.ingsw.view.tui.section.TUILeaderboardSection;
import it.polimi.ingsw.view.tui.section.TUIRankingSection;

import java.util.List;

public class TUIGameEndScreen extends TUIScreen {
    public TUIGameEndScreen(ClientController clientController) {
        super(clientController);

        registry = new ActionRegistry()
            .register(new TUIDisconnectAction(clientController))
            .register(new TUIShowLeaderboardAction(clientController))
            .register(new TUILeaveLobbyAction(clientController));

        sections = List.of(
            new TUIClientInfoSection(),
            new TUIActionSection(registry),
            new TUIRankingSection(),
            new TUILeaderboardSection()
        );
    }
}
