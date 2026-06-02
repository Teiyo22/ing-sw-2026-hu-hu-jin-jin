package it.polimi.ingsw.view.tui.section;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.utils.view.Formatter;
import it.polimi.ingsw.view.tui.action.TUIAction;
import it.polimi.ingsw.view.tui.action.ActionRegistry;

public class TUIActionSection implements TUISection {
    private final ActionRegistry actionRegistry;

    public TUIActionSection(ActionRegistry actionRegistry) {
        this.actionRegistry = actionRegistry;
    }

    @Override
    public void render(ClientController clientController) {
        System.out.println();
        System.out.println(Formatter.separatorLine("Available Actions"));

        for(TUIAction action: actionRegistry.enabled())
            System.out.println(Formatter.line(action.toString()));

        System.out.println(Formatter.separatorLine(""));
    }

    @Override
    public boolean isVisible(ClientController clientController) {
        return  true;
    }
}
