package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.client.info.CardPickStateInfo;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.controller.client.action.CardPickPlayerAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExtraActionState extends GameState {
    private Player currPlayer = null;
    private int solvedExtraActions = -1;

    public ExtraActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
    }

    public void setCurrPlayer(Player player){
       currPlayer = player;
    }

    /**
     * Applies the effects of extra action buildings which effectively initialize the player for the extra action.
     * If no player is found, then pass to {@link RoundEndState}
     * */
    @Override
    public void update() {
        solvedExtraActions++;
        buildingHandler.applyExtraActionEffects(this, solvedExtraActions);

        if(currPlayer == null) {
            game.setGameState(new RoundEndState(game, buildingHandler));
            game.getGameState().update();
        } else if (!canPick()) {
            game.getLobbyState().notifyOfferResolution(currPlayer, new HashSet<>(), new HashSet<>());
            update();
        }
    }

    private boolean canPick() {
        if (!game.getBoard().getTopRow().getCharacterCards().isEmpty())
            return true;

        for (AbstractBuilding b: game.getBoard().getTopRow().getBuildingCards())
            if (b.getCost() - currPlayer.getTribe().getBuilderDiscount() <= currPlayer.getFood())
                return true;

        return false;
    }

    @Override
    public String[] validate(CardPickPlayerAction action) {
        List<String> errors = new ArrayList<>();

        if (!validatePlayer(action.getPlayer()))
            errors.add("Actions are only allow during your turn");

        if (!validatePickCount(action.getTopPicks(), action.getBottomPicks()))
            errors.add("Invalid number of picks (required picks: 1 from top row");

        if (!validateIDList(action.getTopPicks(), game.getBoard().getTopRow()))
            errors.add("Invalid top row card ID(s)");

        if (!validateFoodCost(action.getTopPicks()))
            errors.add("Not enough food for the building(s)");

        return errors.toArray(new String[0]);
    }

    private boolean validatePlayer(Player player) {
        return player.equals(currPlayer);
    }

    private boolean validatePickCount(Set<Integer> top, Set<Integer> bottom) {
        return top.size() == 1 && bottom.isEmpty();
    }

    private boolean validateIDList(Set<Integer> picks, Row row) {
        Set<Integer> rowCardIDs = row.getPickableCards().stream()
            .map(AbstractCard::getID)
            .collect(Collectors.toSet());

        return rowCardIDs.containsAll(picks);
    }

    private boolean validateFoodCost(Set<Integer> top) {
        Board board = game.getBoard();

        int buildingCost = board.getTopRow().getBuildingCards().stream()
            .filter(b -> top.contains(b.getID()))
            .mapToInt(AbstractBuilding::getCost)
            .map(c -> Math.max(0, c - currPlayer.getTribe().getBuilderDiscount()))
            .sum();

        return buildingCost <= currPlayer.getFood();
    }

    @Override
    public ModelStateInfo getModelStateInfo() {
        return new CardPickStateInfo(currPlayer, -1, game.getBoard().getDeck().getCurrentEra());
    }
}
