package it.polimi.ingsw.view.command;

import it.polimi.ingsw.controller.client.ClientController;

public class PickOfferCommand implements Command {
    final private ClientController clientController;
    final private int offerID;

    public PickOfferCommand(ClientController clientController, int offerID) {
        this.clientController = clientController;
        this.offerID = offerID;
    }

    @Override
    public void execute() {
        clientController.executeCommand(() -> {
            clientController.getServer().requestOffer(clientController.getID(),
                    clientController.getCurrLobby().getLobbyID(), offerID);
        });
    }
}
