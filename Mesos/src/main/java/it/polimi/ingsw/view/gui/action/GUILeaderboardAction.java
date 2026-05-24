package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.LeaderboardCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUILeaderboardAction extends AbstractAction {
    private final ClientController clientController;

    public GUILeaderboardAction(ClientController clientController){
        this.clientController = clientController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new LeaderboardCommand(clientController).execute();
    }
}
