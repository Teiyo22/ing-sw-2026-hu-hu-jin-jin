package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.common.info.CardPickStateInfo;
import it.polimi.ingsw.controller.common.info.ModelStateInfo;
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

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoundActionState extends GameState {
    private Player currPlayer;
    private final OfferTile[] offerTrack;

    private int solvedOffers = 0;
    private int assignedPlayers = 0;

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
            game.getLobbyState().notifyOfferResolution(currPlayer);
        }

        for (; solvedOffers < offerTrack.length && offerTrack[solvedOffers].getAssignedPlayer() == null; solvedOffers++);

        if (offerTrack.length == solvedOffers) {
            game.setGameState(new ExtraActionState(game, buildingHandler));
            game.getGameState().update();
            return;
        }

        currPlayer = offerTrack[solvedOffers].getAssignedPlayer();
        offerTrack[solvedOffers].solveBonusFood();

        if (offerTrack[solvedOffers].getTopRowPickable() * game.getBoard().getTopRow().getPickableCardCount() == 0 &&
            offerTrack[solvedOffers].getBottomRowPickable() * game.getBoard().getBottomRow().getPickableCardCount() == 0)
            update();
    }

    /**
     * After a player has finished his action turn, he is assigned to the correct order slot.
     *
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
               validateIDList(action.getTopPicks(), game.getBoard().getTopRow()) +
               validateIDList(action.getBottomPicks(), game.getBoard().getBottomRow()) +
               validatePickCount(action.getTopPicks(), action.getBottomPicks()) +
               validateFoodCost(action.getTopPicks(), action.getBottomPicks());
    }

    private String validateIDList(Set<Integer> picks, Row row) {
        Set<Integer> rowCardIDs = row.getPickableCards().stream()
                .map(AbstractCard::getID)
                .collect(Collectors.toSet());

        return rowCardIDs.containsAll(picks) ? "" : "The card ID(s) must be present |";
    }

    private String validatePickCount(Set<Integer> top , Set<Integer> bottom) {
        int topPickCount, bottomPickCount;

        OfferTile offerTile = offerTrack[solvedOffers];
        topPickCount = offerTile.getTopRowPickable();
        bottomPickCount = offerTile.getBottomRowPickable();

        return top.size() <= topPickCount && bottom.size() <= bottomPickCount ? "" : "Invalid number of picks |";
    }

    private String validateFoodCost(Set<Integer> top, Set<Integer> bottom) {
        Board board = game.getBoard();

        int buildingCost = Stream.concat(
                board.getTopRow().getBuildingCards().stream().filter(b -> top.contains(b.getID())),
                board.getBottomRow().getBuildingCards().stream().filter(b -> bottom.contains(b.getID())))
                .mapToInt(AbstractBuilding::getCost)
                .map(c -> Math.max(0, c - currPlayer.getTribe().getBuilderDiscount()))
                .sum();

        return buildingCost <= currPlayer.getFood() ? "" : "Not enough food for the buildings |";
    }

    private String validatePlayer(Player player) {
        return player.equals(currPlayer) ? "" : "You can only play during your turn |";
    }
}
