package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.action.*;
import it.polimi.ingsw.view.tui.section.TUIActionSection;
import it.polimi.ingsw.view.tui.section.TUIClientInfoSection;
import it.polimi.ingsw.view.tui.section.TUILobbyInfoSection;
import it.polimi.ingsw.view.tui.section.TUILobbyListSection;

import java.util.List;


    public class TUILobbySelectionScreen extends  TUIScreen {
    public TUILobbySelectionScreen(ClientController clientController) {
        super(clientController);

        registry = new ActionRegistry()
                .register(new TUIDisconnectAction(clientController))
                .register(new TUICreateLobbyAction(clientController))
                .register(new TUILobbyInfoAction(clientController))
                .register(new TUIJoinLobbyAction(clientController))
                .register(new TUILeaveLobbyAction(clientController))
                .register(new TUIStartLobbyAction(clientController));

        sections = List.of(
                new TUIClientInfoSection(),
                new TUIActionSection(registry),
                new TUILobbyInfoSection(),
                new TUILobbyListSection()
        );
    }
}
