package it.polimi.ingsw.view.gui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.command.PickCardCommand;
import it.polimi.ingsw.view.gui.section.RowSection;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUICardPickAction extends AbstractAction {
    private final ClientController clientController;
    private final RowSection topRowSection;
    private final RowSection bottomRowSection;

    public GUICardPickAction(ClientController clientController, RowSection topRowSection, RowSection bottomRowSection) {
        super("Pick Cards");
        this.clientController = clientController;
        this.topRowSection = topRowSection;
        this.bottomRowSection = bottomRowSection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new PickCardCommand(clientController, topRowSection.getPicks(), bottomRowSection.getPicks()).execute();
    }
}
