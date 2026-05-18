package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.LeaveLobbyCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUILeaveLobbyAction extends AbstractAction {
    private final ClientController clientController;

    public GUILeaveLobbyAction(ClientController clientController) {
        super("Leave");
        this.clientController = clientController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (clientController.getCurrLobby() == null) return;
        new LeaveLobbyCommand(clientController, clientController.getCurrLobby().getLobbyID()).execute();
    }
}
