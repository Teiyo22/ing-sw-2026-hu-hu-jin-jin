package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.components.CardPicksListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUICardPickAction extends AbstractAction {
    private final ClientController clientController;
    private final CardPicksListener topListener;
    private final CardPicksListener bottomListener;

    public GUICardPickAction(ClientController clientController, CardPicksListener topListener, CardPicksListener bottomListener) {
        super("Pick Cards");
        this.clientController = clientController;
        this.topListener = topListener;
        this.bottomListener = bottomListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clientController.getServer().requestCards(clientController.getID(), clientController.getCurrLobby().getLobbyID(),
                topListener.getPicks(), bottomListener.getPicks());
    }
}
