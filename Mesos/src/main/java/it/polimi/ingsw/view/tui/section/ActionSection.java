package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.Formatter;
import it.polimi.ingsw.view.tui.action.Action;
import it.polimi.ingsw.view.tui.action.ActionRegistry;

public class ActionSection implements Section {
    private final ActionRegistry actionRegistry;

    public ActionSection(ActionRegistry actionRegistry) {
        this.actionRegistry = actionRegistry;
    }

    @Override
    public void render(ClientController clientController) {
        System.out.println(Formatter.separatorLine("Lobby Selection"));
        System.out.println(Formatter.line("Available actions:"));

        for(Action action: actionRegistry.enabled())
            System.out.println(Formatter.line(action.toString()));
    }
}
