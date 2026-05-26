package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.common.info.CardPickStateInfo;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.action.CardPickPlayerAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.player.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public String validate(CardPickPlayerAction action) {
        return validatePlayer(action.getPlayer()) +
               validatePickCount(action.getTopPicks(), action.getBottomPicks()) +
               validateIDList(action.getTopPicks(), game.getBoard().getTopRow()) +
               validateFoodCost(action.getTopPicks());
    }

    private String validatePlayer(Player player) {
        return player.equals(currPlayer) ? "" : "You can only play during your turn | ";
    }

    private String validatePickCount(Set<Integer> top, Set<Integer> bottom) {
        return top.size() == 1 && bottom.isEmpty() ? "" : "Invalid number of picks | ";
    }

    private String validateIDList(Set<Integer> picks, Row row) {
        Set<Integer> rowCardIDs = row.getPickableCards().stream()
            .map(AbstractCard::getID)
            .collect(Collectors.toSet());

        return rowCardIDs.containsAll(picks) ? "" : "The card ID(s) must be present | ";
    }

    private String validateFoodCost(Set<Integer> top) {
        Board board = game.getBoard();

        int buildingCost = board.getTopRow().getBuildingCards().stream()
            .filter(b -> top.contains(b.getID()))
            .mapToInt(AbstractBuilding::getCost)
            .map(c -> Math.max(0, c - currPlayer.getTribe().getBuilderDiscount()))
            .sum();

        return buildingCost <= currPlayer.getFood() ? "" : "Not enough food for the buildings | ";
    }

    @Override
    public ModelStateInfo getModelStateInfo() {
        return new CardPickStateInfo(currPlayer, -1, game.getBoard().getDeck().getCurrentEra());
    }
}
