package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.PickOfferCommand;
import it.polimi.ingsw.view.gui.components.OfferPickListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUIOfferPickAction extends AbstractAction {
    private final ClientController clientController;
    private final OfferPickListener offerPickListener;

    public GUIOfferPickAction(ClientController clientController, OfferPickListener offerPickListener) {
        super("Pick Offer");
        this.clientController = clientController;
        this.offerPickListener = offerPickListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new PickOfferCommand(clientController, offerPickListener.getSelectedOfferIndex());
    }
}
