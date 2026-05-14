package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.view.command.PickOfferCommand;

import java.util.Optional;

public class PickOfferAction implements Action {
    final private ClientController clientController;
    final private int argCount = 1;

    public PickOfferAction(ClientController clientController) {
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
        TurnState turnState = clientController.getCurrLobby().getTurnState();
        return turnState != null && turnState.canPickOffer();
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        Integer offerID;

        if (args.length != argCount + 1)
            return Optional.of("Invalid number of arguments");

        offerID = parseOfferID(args[1]);
        if (offerID == null)
            return Optional.of("Offer ID must be an integer");

        clientController.getCurrLobby().setIdleTurnState();
        new PickOfferCommand(offerID).execute(clientController);
        return Optional.empty();
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
