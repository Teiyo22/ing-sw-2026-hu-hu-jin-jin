package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.action.ActionRegistry;
import it.polimi.ingsw.view.tui.action.DisconnectAction;
import it.polimi.ingsw.view.tui.action.LoginAction;
import it.polimi.ingsw.view.tui.section.ActionSection;

import java.util.List;

public class TUILoginScreen extends TUIScreen {
    public TUILoginScreen(ClientController clientController) {
        super(clientController);

        registry = new ActionRegistry()
                .register(new DisconnectAction(clientController))
                .register(new LoginAction(clientController));

        sections = List.of(
                new ActionSection(registry)
        );
    }
}
