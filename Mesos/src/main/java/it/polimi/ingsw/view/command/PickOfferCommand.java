package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.action.OfferPickPlayerAction;

public class PickOfferCommand implements Command {
    final private ClientController clientController;
    final private int offerID;

    public PickOfferCommand(ClientController clientController, int offerID) {
        this.clientController = clientController;
        this.offerID = offerID;
    }

    @Override
    public void execute() {
        clientController.submitRequest(
                () -> clientController.requestAction(new OfferPickPlayerAction(offerID))
        );
    }
}
