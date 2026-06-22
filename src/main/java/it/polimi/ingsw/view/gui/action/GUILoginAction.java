package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.LoginCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILoginAction extends AbstractAction {
    private final ClientController clientController;
    private final JTextField text;

    public GUILoginAction(ClientController clientController, JTextField text){
        super("Login");
        this.clientController = clientController;
        this.text = text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = text.getText();
        text.setText("");
        new LoginCommand(clientController,username).execute();
    }
}
