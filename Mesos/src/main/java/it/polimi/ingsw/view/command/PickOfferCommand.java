package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.action.OfferPickPlayerAction;

public class PickOfferCommand implements Command {
    int offerID;

    public PickOfferCommand(int offerID) {
        this.offerID = offerID;
    }

    @Override
    public void execute(ClientController clientController) {
        clientController.executeCommand(() -> {
            clientController.getServer().requestAction(clientController.getID(),
                    clientController.getCurrLobby().getLobbyID(), new OfferPickPlayerAction(offerID));
        });
    }
}
