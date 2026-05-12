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
        if (topRow == null)
            return Optional.of("Top row must contain only valid card IDs");

        bottomRow = parseIDList(args[2], false);
        if (bottomRow == null)
            return Optional.of("Bottom row must contain only valid card IDs");

        if (!validatePickCount(topRow, bottomRow))
            return Optional.of("Pick count exceeded");

        if (!validateFoodCost(topRow, bottomRow))
            return Optional.of("Food cost exceeded");

        clientController.getCurrLobby().setIdleTurnState();
        new PickCardCommand(clientController, topRow, bottomRow).execute();
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
             Set<Integer> set = Arrays.stream(split)
                     .map(String::trim)
                     .map(Integer::parseInt)
                     .collect(Collectors.toSet());

             if (validateIDList(set, top))
                 return set;

             return null;
         } catch (NumberFormatException e) {
             return null;
         }
    }

    private boolean validateIDList(Set<Integer> set, boolean top) {
        Row row = top ? clientController.getCurrLobby().getBoard().getTopRow() : clientController.getCurrLobby().getBoard().getBottomRow();

        Set<Integer> rowCardIDs = Stream.concat(
                row.getBuildingCards().stream().map(b -> (AbstractCard) b),
                row.getCharacterCards().stream().map(c -> (AbstractCard) c))
                .map(AbstractCard::getID)
                .collect(Collectors.toSet());

        return rowCardIDs.containsAll(set);
    }

    private boolean validatePickCount(Set<Integer> top , Set<Integer> bottom) {
        int idx = clientController.getCurrLobby().getTurnState().getIndex();
        int topPickCount, bottomPickCount;

        if (idx >= 0) {
            OfferTile offerTile = clientController.getCurrLobby().getBoard().getOfferTrack()[idx];
            topPickCount = offerTile.getTopRowPickable();
            bottomPickCount = offerTile.getBottomRowPickable();
        } else {
            topPickCount = 1;
            bottomPickCount = 0;
        }

        return top.size() <= topPickCount && bottom.size() <= bottomPickCount;
    }

    private boolean validateFoodCost(Set<Integer> top, Set<Integer> bottom) {
        Player currPlayer = clientController.getCurrLobby().getCurrPlayer();
        Board board = clientController.getCurrLobby().getBoard();

        int foodCost = Stream.concat(
                board.getTopRow().getBuildingCards().stream().filter(b -> top.contains(b.getID())),
                board.getBottomRow().getBuildingCards().stream().filter(b -> bottom.contains(b.getID())))
                .mapToInt(AbstractBuilding::getCost)
                .sum();

        return foodCost - currPlayer.getTribe().getBuilderDiscount() <= currPlayer.getFood();
    }
}
