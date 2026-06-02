package it.polimi.ingsw.view.tui.action;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.Lobby;
import it.polimi.ingsw.controller.client.turn.TurnState;
import it.polimi.ingsw.view.command.PickCardCommand;

import java.util.*;
import java.util.stream.Collectors;

public class TUIPickCardAction implements TUIAction {
    final private ClientController clientController;
    final private int argCount = 2;

    public TUIPickCardAction(ClientController clientController) {
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
        Lobby currLobby = clientController.getCurrLobby();
        if (currLobby == null)
            return false;

        TurnState turnState = clientController.getCurrLobby().getTurnState();
        return turnState != null && turnState.canPickCard();
    }

    @Override
    public boolean parseAction(String[] args) {
        Set<Integer> topRow, bottomRow;

        if (args.length != argCount + 1)
            return false;
        topRow = parseIDList(args[1]);
        bottomRow = parseIDList(args[2]);
        if (topRow == null || bottomRow == null) {
            return false;
        }

        new PickCardCommand(clientController, topRow, bottomRow).execute();
        return true;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s] [<Top Row ID>,...] [<Bottom Row ID>,...]", key(), label());
    }

    private Set<Integer> parseIDList(String input) {
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
