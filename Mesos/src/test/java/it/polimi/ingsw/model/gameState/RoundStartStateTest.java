package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.client.action.OfferPickPlayerAction;
import it.polimi.ingsw.controller.client.info.OfferPickStateInfo;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundStartStateTest {
    private RoundStartState roundStartState;
    private List<Player> players;
    private Game game;
    private BuildingHandler handler;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        game = new Game(PlayerConfig.TWO, players);
        handler = new BuildingHandler();
        roundStartState = new RoundStartState(game, handler);
        game.setGameState(roundStartState);
    }

    @Test
    void updateTest() {
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        OfferTile ot1 = game.getBoard().getOfferTrack()[0];
        OfferTile ot2 = game.getBoard().getOfferTrack()[1];

        roundStartState.update();
        assertNotNull(game.getBoard().getOrderTile()[0].getAssignedPlayer());
        assertNotNull(game.getBoard().getOrderTile()[1].getAssignedPlayer());
        assertEquals(game.getBoard().getOrderTile()[0].getAssignedPlayer(), roundStartState.getCurrPlayer());

        ot1.setPlayer(p1);
        roundStartState.update();
        assertNull(game.getBoard().getOrderTile()[0].getAssignedPlayer());
        assertEquals(game.getBoard().getOrderTile()[1].getAssignedPlayer(), roundStartState.getCurrPlayer());

        ot2.setPlayer(p2);
        roundStartState.update();
        assertNull(game.getBoard().getOrderTile()[1].getAssignedPlayer());
        assertEquals(RoundActionState.class, game.getGameState().getClass());

    }


    @Test
    void validateTest() {
        Player currPlayer = players.get(0);
        Player wrongPlayer = players.get(1);
        roundStartState.setCurrPlayer(currPlayer);

        OfferPickPlayerAction wrongTurnAction = new OfferPickPlayerAction(0);
        wrongTurnAction.setPlayer(wrongPlayer);
        String[] errorsTurn = roundStartState.validate(wrongTurnAction);

        assertEquals("Actions are only allowed during your turn", errorsTurn[0]);

        OfferPickPlayerAction negativeIndexAction = new OfferPickPlayerAction(-1);
        negativeIndexAction.setPlayer(currPlayer);
        String[] errorsNegative = roundStartState.validate(negativeIndexAction);

        assertEquals("Invalid offer index", errorsNegative[0]);

        int outOfBoundsIndex = game.getBoard().getOfferTrack().length;
        OfferPickPlayerAction outOfBoundsAction = new OfferPickPlayerAction(outOfBoundsIndex);
        outOfBoundsAction.setPlayer(currPlayer);
        String[] errorsOutOfBounds = roundStartState.validate(outOfBoundsAction);

        assertEquals("Invalid offer index", errorsOutOfBounds[0]);


        game.getBoard().getOfferTrack()[0].setPlayer(wrongPlayer);
        OfferPickPlayerAction alreadyPickedAction = new OfferPickPlayerAction(0);
        alreadyPickedAction.setPlayer(currPlayer);
        String[] errorsAlreadyPicked = roundStartState.validate(alreadyPickedAction);

        assertEquals("Offer already picked by another player", errorsAlreadyPicked[0]);


        OfferPickPlayerAction validAction = new OfferPickPlayerAction(1);
        validAction.setPlayer(currPlayer);
        String[] noErrors = roundStartState.validate(validAction);

        assertEquals(0, noErrors.length);
    }

    @Test
    void getModelStateInfoTest() {
        Player currPlayer = players.get(0);
        roundStartState.setCurrPlayer(currPlayer);
        roundStartState.setAssignedSlots(1);

        var info = roundStartState.getModelStateInfo();

        assertInstanceOf(OfferPickStateInfo.class, info);
    }

    @Test
    void copyTest() {
        Player currPlayer = players.get(0);
        roundStartState.setCurrPlayer(currPlayer);
        roundStartState.setAssignedSlots(2);

        GameState copiedState = roundStartState.copy();

        assertInstanceOf(RoundStartState.class, copiedState);

        RoundStartState castedCopy = (RoundStartState) copiedState;
        assertEquals(roundStartState.getCurrPlayer(), castedCopy.getCurrPlayer());
    }
    @Test
    void fixReferencesTest() {
        String targetName = "Ciccio";

        Player detachedPlayer = new Player(targetName, Totem.BLACK);
        roundStartState.setCurrPlayer(detachedPlayer);

        Player officialPlayer = game.getPlayers().stream()
                .filter(p -> p.getName().equals(targetName))
                .findFirst()
                .orElseThrow();

        assertNotSame(officialPlayer, roundStartState.getCurrPlayer());

        roundStartState.fixReferences(game);

        assertSame(officialPlayer, roundStartState.getCurrPlayer());
    }
}