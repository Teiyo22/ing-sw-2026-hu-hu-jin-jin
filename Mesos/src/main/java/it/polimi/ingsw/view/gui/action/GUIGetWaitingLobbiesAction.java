package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.GetWaitingLobbiesCommand;
import it.polimi.ingsw.view.tui.action.GetWaitingLobbiesAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUIGetWaitingLobbiesAction extends AbstractAction {
    private final ClientController clientController;

    public GUIGetWaitingLobbiesAction(ClientController clientController) {
        super("Refresh");
        this.clientController = clientController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new GetWaitingLobbiesCommand(clientController).execute();
    }
}
