package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.common.info.CardPickStateInfo;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
import it.polimi.ingsw.controller.server.lobby.states.LobbyState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.action.CardPickPlayerAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.player.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoundActionState extends GameState {
    private Player currPlayer;
    private final OfferTile[] offerTrack;

    private int solvedOffers = 0;
    private int assignedPlayers = 0;

    private int topRowPickable = 0;
    private int bottomRowPickable = 0;

    public RoundActionState(Game game, BuildingHandler buildingHandler) {
        super(game, buildingHandler);
        offerTrack = game.getBoard().getOfferTrack();
    }

    /**
     * Finds the next offer tile to be resolved and sets the player assigned to it as current player.
     * If the offer tile also provides bonus food, the player gains the specified amount.
     * If all offer tiles have been resolved, the state changes to {@link ExtraActionState}.
     *
     */
    @Override
    public void update() {
        if (currPlayer != null) {
            assignToOrderSlot(offerTrack[solvedOffers]);
        }

        for (; solvedOffers < offerTrack.length && offerTrack[solvedOffers].getAssignedPlayer() == null; solvedOffers++)
            ;

        if (offerTrack.length == solvedOffers) {
            game.setGameState(new ExtraActionState(game, buildingHandler));
            game.getGameState().update();
            return;
        }

        currPlayer = offerTrack[solvedOffers].getAssignedPlayer();
        offerTrack[solvedOffers].solveBonusFood();
        computePickable();

        if (topRowPickable == 0 && bottomRowPickable == 0) {
            game.getLobbyState().notifyOfferResolution(currPlayer, new HashSet<>(), new HashSet<>());
            update();
        }
    }

    /**
     * Computes how many cards the player has to pick from the top and bottom row.
     *
     */
    private void computePickable() {
        topRowPickable = game.getBoard().getTopRow().getCharacterCards().size();
        bottomRowPickable = game.getBoard().getBottomRow().getCharacterCards().size();

        List<Integer> topBuildingCost = getBuildingsCost(game.getBoard().getTopRow());
        List<Integer> bottomBuildingCost = getBuildingsCost(game.getBoard().getBottomRow());

        int buildingDiscount = currPlayer.getTribe().getBuilderDiscount();

        int food = currPlayer.getFood();
        for (Integer c : topBuildingCost)
            if (c - buildingDiscount <= food) {
                topRowPickable++;
                food -= Math.max(0, c - buildingDiscount);
            }

        food = currPlayer.getFood();
        for (Integer c : bottomBuildingCost)
            if (c - buildingDiscount <= food) {
                bottomRowPickable++;
                food -= Math.max(0, c - buildingDiscount);
            }

        topRowPickable = Math.min(topRowPickable, offerTrack[solvedOffers].getTopRowPickable());
        bottomRowPickable = Math.min(bottomRowPickable, offerTrack[solvedOffers].getBottomRowPickable());
    }

    private List<Integer> getBuildingsCost(Row row) {
        return row.getBuildingCards().stream()
            .map(AbstractBuilding::getCost)
            .sorted()
            .toList();
    }

    /**
     * After a player has finished his action turn, he is assigned to the correct order slot.
     */
    public void assignToOrderSlot(OfferTile offerTile) {
        OrderSlot orderSlot = game.getBoard().getOrderTile()[assignedPlayers];

        orderSlot.setPlayer(offerTile.getAssignedPlayer());
        orderSlot.solveDeltaFood();

        offerTile.setPlayer(null);
        buildingHandler.applyOrderTileEffects(orderSlot);

        solvedOffers++;
        assignedPlayers++;
    }

    @Override
    public ModelStateInfo getModelStateInfo() {
        return new CardPickStateInfo(currPlayer, solvedOffers, game.getBoard().getDeck().getCurrentEra());
    }

    @Override
    public String validate(CardPickPlayerAction action) {
        return validatePlayer(action.getPlayer()) +
            validatePickCount(action.getTopPicks(), action.getBottomPicks()) +
            validateIDList(action.getTopPicks(), game.getBoard().getTopRow()) +
            validateIDList(action.getBottomPicks(), game.getBoard().getBottomRow()) +
            validateFoodCost(action.getTopPicks(), action.getBottomPicks());
    }

    private String validatePlayer(Player player) {
        return player.equals(currPlayer) ? "" : "You can only play during your turn | ";
    }

    private String validatePickCount(Set<Integer> top, Set<Integer> bottom) {
        return top.size() == topRowPickable && bottom.size() == bottomRowPickable
            ? ""
            : "Invalid number of picks | ";
    }

    private String validateIDList(Set<Integer> picks, Row row) {
        Set<Integer> rowCardIDs = row.getPickableCards().stream()
            .map(AbstractCard::getID)
            .collect(Collectors.toSet());

        return rowCardIDs.containsAll(picks) ? "" : "The card ID(s) must be present | ";
    }

    private String validateFoodCost(Set<Integer> top, Set<Integer> bottom) {
        Board board = game.getBoard();

        int buildingCost = Stream.concat(
             board.getTopRow().getBuildingCards().stream().filter(b -> top.contains(b.getID())),
             board.getBottomRow().getBuildingCards().stream().filter(b -> bottom.contains(b.getID())))
            .mapToInt(AbstractBuilding::getCost)
            .map(c -> Math.max(0, c - currPlayer.getTribe().getBuilderDiscount()))
            .sum();

        return buildingCost <= currPlayer.getFood() ? "" : "Not enough food for the buildings | ";
    }
}
