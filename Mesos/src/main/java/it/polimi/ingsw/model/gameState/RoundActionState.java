package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.client.info.CardPickStateInfo;
import it.polimi.ingsw.controller.client.info.ModelStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.controller.client.action.CardPickPlayerAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.board.Row;
import it.polimi.ingsw.model.card.AbstractCard;
import it.polimi.ingsw.model.card.building.AbstractBuilding;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoundActionState extends GameState {
    transient private OfferTile[] offerTrack;

    private Player currPlayer;

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
    public String[] validate(CardPickPlayerAction action) {
        List<String> errors = new ArrayList<>();

        if (!validatePlayer(action.getPlayer()))
            errors.add("Actions are only allowed during your turn");

        if (!validatePickCount(action.getTopPicks(), action.getBottomPicks()))
            errors.add(String.format("Invalid number of picks (Required picks: %d from top row, %d from bottom row)",
                topRowPickable, bottomRowPickable));

        if (!validateIDList(action.getTopPicks(), game.getBoard().getTopRow()))
            errors.add("Invalid top row card ID(s)");

        if (!validateIDList(action.getBottomPicks(), game.getBoard().getBottomRow()))
            errors.add("Invalid bottom row card ID(s)");

        if (!validateFoodCost(action.getTopPicks(), action.getBottomPicks()))
            errors.add("Not enough food for the building(s)");

        return errors.toArray(new String[0]);
    }

    private boolean validatePlayer(Player player) {
        return player.equals(currPlayer);
    }

    private boolean validatePickCount(Set<Integer> top, Set<Integer> bottom) {
        return top.size() == topRowPickable && bottom.size() == bottomRowPickable;
    }

    private boolean validateIDList(Set<Integer> picks, Row row) {
        Set<Integer> rowCardIDs = row.getPickableCards().stream()
            .map(AbstractCard::getID)
            .collect(Collectors.toSet());

        return rowCardIDs.containsAll(picks);
    }

    private boolean validateFoodCost(Set<Integer> top, Set<Integer> bottom) {
        Board board = game.getBoard();

        int buildingCost = Stream.concat(
             board.getTopRow().getBuildingCards().stream().filter(b -> top.contains(b.getID())),
             board.getBottomRow().getBuildingCards().stream().filter(b -> bottom.contains(b.getID())))
            .mapToInt(AbstractBuilding::getCost)
            .map(c -> Math.max(0, c - currPlayer.getTribe().getBuilderDiscount()))
            .sum();

        return buildingCost <= currPlayer.getFood();
    }

    @Override
    public GameState copy() {
        RoundActionState copy = new RoundActionState(game, buildingHandler);
        copy.currPlayer = currPlayer.shallowCopy();
        copy.solvedOffers = solvedOffers;
        copy.assignedPlayers = assignedPlayers;

        copy.topRowPickable = topRowPickable;
        copy.bottomRowPickable = bottomRowPickable;

        return copy;
    }

    @Override
    public void fixReferences(Game game) {
        super.fixReferences(game);
        offerTrack = game.getBoard().getOfferTrack();
        if (currPlayer != null)
            for (Player p: game.getPlayers())
                if (currPlayer.equals(p))
                    currPlayer = p;
    }

    public void setCurrPlayer(Player p){
        currPlayer = p;
    }

    public Player getCurrPlayer(){
        return currPlayer;
    }
}
