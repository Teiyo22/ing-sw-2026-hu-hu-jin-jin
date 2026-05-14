package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.command.PickCardCommand;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PickCardAction implements Action {
    final private ClientController clientController;
    final private int argCount = 2;

    public PickCardAction(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public String key() {
        return "4";
    }

    @Override
    public String label() {
        return "Card";
    }

    @Override
    public boolean isEnabled() {
        TurnState turnState = clientController.getCurrLobby().getTurnState();
        return turnState != null && turnState.canPickCard();
    }

    @Override
    public Optional<String> parseAction(String[] args) {
        Set<Integer> topRow, bottomRow;

        if (args.length != argCount + 1)
            return Optional.of("Invalid number of arguments");

        topRow = parseIDList(args[1], true);
        bottomRow = parseIDList(args[2], false);
        if (topRow == null || bottomRow == null) {
            return Optional.of("Card IDs must be integers separated by commas");
        }

        clientController.getCurrLobby().setIdleTurnState();
        new PickCardCommand(topRow, bottomRow).execute(clientController);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] {<Top Row ID>,...} {<Bottom Row ID>,...}", key(), label());
    }

    private Set<Integer> parseIDList(String input, boolean top) {
        String stripped = input.substring(1, input.length() - 1);

        if (stripped.isEmpty())
            return new HashSet<>();

        String[] split = stripped.split(",");

        try {
            return Arrays.stream(split)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
         } catch (NumberFormatException e) {
             return null;
         }
    }
}
