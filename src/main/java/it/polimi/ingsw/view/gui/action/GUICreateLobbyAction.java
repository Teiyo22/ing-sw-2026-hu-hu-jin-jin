package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.view.command.CreateLobbyCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUICreateLobbyAction extends AbstractAction {
    final private ClientController clientController;
    final private JComboBox<Integer> playerNumBox;
    final private JComboBox<Totem> totemBox;

    public GUICreateLobbyAction(ClientController clientController, JComboBox<Integer> playerNumBox, JComboBox<Totem> totemBox) {
        super("Create");
        this.clientController = clientController;
        this.playerNumBox = playerNumBox;
        this.totemBox = totemBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new CreateLobbyCommand(clientController,
                (Integer) playerNumBox.getSelectedItem(), new Player(clientController.getID(), (Totem) totemBox.getSelectedItem()).getTotem()).execute();
    }
}
