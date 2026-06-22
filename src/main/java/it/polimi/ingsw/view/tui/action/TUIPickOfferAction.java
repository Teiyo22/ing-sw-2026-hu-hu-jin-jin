package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.view.command.PickOfferCommand;

public class TUIPickOfferAction implements TUIAction {
    final private ClientController clientController;
    final private int argCount = 1;

    public TUIPickOfferAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "3";
    }

    @Override
    public String label() {
        return "Offer";
    }

    @Override
    public boolean isEnabled() {
        Lobby currLobby = clientController.getCurrLobby();
        if (currLobby == null) {
            return false;
        }

        TurnState turnState = currLobby.getTurnState();
        return turnState != null && turnState.canPickOffer();
    }

    @Override
    public boolean parseAction(String[] args) {
        Integer offerID;

        if (args.length != argCount + 1)
            return false;

        offerID = parseOfferID(args[1]);
        if (offerID == null)
            return false;

        new PickOfferCommand(clientController, offerID).execute();
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] <Offer ID>", key(), label());
    }

    private Integer parseOfferID(String input) {
         try {
             return Integer.parseInt(input);
          } catch (NumberFormatException e) {
              return null;
          }
    }
}
