package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.StartLobbyCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUIStartLobbyAction extends AbstractAction {
    private final ClientController clientController;

    public GUIStartLobbyAction(ClientController clientController) {
        super("Start");
        this.clientController = clientController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new StartLobbyCommand(clientController).execute();
    }
}
