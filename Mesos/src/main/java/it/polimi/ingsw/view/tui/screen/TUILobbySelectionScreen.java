package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.action.*;
import it.polimi.ingsw.view.tui.section.ActionSection;
import it.polimi.ingsw.view.tui.section.ClientInfoSection;
import it.polimi.ingsw.view.tui.section.LobbyInfoSection;
import it.polimi.ingsw.view.tui.section.LobbyListSection;

import java.util.List;


    public class TUILobbySelectionScreen extends  TUIScreen {
    public TUILobbySelectionScreen(ClientController clientController) {
        super(clientController);

        registry = new ActionRegistry()
                .register(new DisconnectAction(clientController))
                .register(new GetWaitingLobbiesAction(clientController))
                .register(new CreateLobbyAction(clientController))
                .register(new LobbyInfoAction(clientController))
                .register(new JoinLobbyAction(clientController))
                .register(new LeaveLobbyAction(clientController))
                .register(new StartLobbyAction(clientController));

        sections = List.of(
                new ClientInfoSection(),
                new ActionSection(registry),
                new LobbyInfoSection(),
                new LobbyListSection()
        );
    }
}
