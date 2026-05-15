package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.JoinLobbyCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUIJoinLobbyAction extends AbstractAction {
    private final ClientController clientController;
    private final Totem totem;

    public GUIJoinLobbyAction(ClientController clientController, Totem totem) {
        super("Join");
        this.clientController = clientController;
        this.totem = totem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new JoinLobbyCommand(clientController, clientController.getCurrLobby().getLobbyID(), totem).execute();
    }
}
