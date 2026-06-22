package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.LeaderboardCommand;
import it.polimi.ingsw.view.gui.screen.GUIGameEndScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUILeaderboardAction extends AbstractAction {
    private final ClientController clientController;
    private final GUIGameEndScreen gameEndScreen;

    public GUILeaderboardAction(ClientController clientController, GUIGameEndScreen gameEndScreen){
        super("Leaderboard");
        this.clientController = clientController;
        this.gameEndScreen = gameEndScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new LeaderboardCommand(clientController).execute();
        gameEndScreen.showLeaderboard();
    }
}
