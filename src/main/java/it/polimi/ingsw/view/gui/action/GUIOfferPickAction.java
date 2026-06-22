package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.PickOfferCommand;
import it.polimi.ingsw.view.gui.section.OfferTrackSection;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUIOfferPickAction extends AbstractAction {
    private final ClientController clientController;
    private final OfferTrackSection offerTrackSection;

    public GUIOfferPickAction(ClientController clientController, OfferTrackSection offerTrackSection) {
        super("Pick Offer");
        this.clientController = clientController;
        this.offerTrackSection = offerTrackSection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new PickOfferCommand(clientController, offerTrackSection.getSelectedOfferIndex()).execute();
    }
}
