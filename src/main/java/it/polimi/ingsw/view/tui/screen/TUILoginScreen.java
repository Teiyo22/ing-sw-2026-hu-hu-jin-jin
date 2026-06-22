package it.polimi.ingsw.view.tui.screen;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.action.ActionRegistry;
import it.polimi.ingsw.view.tui.action.TUIDisconnectAction;
import it.polimi.ingsw.view.tui.action.TUILoginAction;
import it.polimi.ingsw.view.tui.section.TUIActionSection;

import java.util.List;

public class TUILoginScreen extends TUIScreen {
    public TUILoginScreen(ClientController clientController) {
        super(clientController);

        registry = new ActionRegistry()
                .register(new TUIDisconnectAction(clientController))
                .register(new TUILoginAction(clientController));

        sections = List.of(
                new TUIActionSection(registry)
        );
    }
}
