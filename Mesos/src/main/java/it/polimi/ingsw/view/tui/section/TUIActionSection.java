package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.tui.Formatter;
import it.polimi.ingsw.view.tui.action.Action;
import it.polimi.ingsw.view.tui.action.ActionRegistry;

public class TUIActionSection implements Section {
    private final ActionRegistry actionRegistry;

    public TUIActionSection(ActionRegistry actionRegistry) {
        this.actionRegistry = actionRegistry;
    }

    @Override
    public void render(ClientController clientController) {
        System.out.println();
        System.out.println(Formatter.separatorLine("Available Actions"));

        for(Action action: actionRegistry.enabled())
            System.out.println(Formatter.line(action.toString()));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        return  true;
    }
}
