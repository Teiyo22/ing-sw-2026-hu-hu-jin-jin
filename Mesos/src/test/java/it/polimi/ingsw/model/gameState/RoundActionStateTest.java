package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.client.action.CardPickPlayerAction;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.CavePaintingBuilding;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoundActionStateTest {
    private Game game;
    private RoundActionState roundActionState;
    private List<Player> players;


    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        game = new Game(PlayerConfig.TWO, players);
        game.setLobbyState(new LobbyRunningState(new LobbyController(1, 2)));

        game.assignTo(players.get(0), 0);
        game.assignTo(players.get(1), 1);
        roundActionState = new RoundActionState(game, new BuildingHandler());
        game.setGameState(roundActionState);
    }

    @Test
    void updateTest() {
        roundActionState.update();
        assertNull(game.getBoard().getOrderTile()[0].getAssignedPlayer());
        assertNull(game.getBoard().getOrderTile()[1].getAssignedPlayer());
        assertEquals(players.get(0), roundActionState.getCurrPlayer());

        game.pick(players.get(0), new HashSet<>(), new HashSet<>());
        assertEquals(players.get(0), game.getBoard().getOrderTile()[0].getAssignedPlayer());
        assertEquals(players.get(1), roundActionState.getCurrPlayer());

        game.pick(players.get(1), new HashSet<>(), new HashSet<>());
        assertEquals(players.get(1), game.getBoard().getOrderTile()[1].getAssignedPlayer());

        assertEquals(RoundStartState.class, game.getGameState().getClass());
    }


    @Test
    void validateTest() {
        Player currPlayer = players.get(0);
        Player wrongPlayer = players.get(1);
        roundActionState.setCurrPlayer(currPlayer);

        CardPickPlayerAction wrongTurnAction = new CardPickPlayerAction(new HashSet<>(), new HashSet<>());
        wrongTurnAction.setPlayer(wrongPlayer);
        String[] errorsTurn = roundActionState.validate(wrongTurnAction);

        assertTrue(List.of(errorsTurn).contains("Actions are only allowed during your turn"));

        Set<Integer> notValidPickCount = new HashSet<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        CardPickPlayerAction wrongPickCountAction = new CardPickPlayerAction(notValidPickCount, new HashSet<>());
        wrongPickCountAction.setPlayer(currPlayer);
        String[] errorsPickCount = roundActionState.validate(wrongPickCountAction);

        boolean hasPickCountError = false;
        for (String err : errorsPickCount) {
            if (err.contains("Invalid number of picks")) hasPickCountError = true;
        }
        assertTrue(hasPickCountError);


        Set<Integer> invalidTopIDs = new HashSet<>(List.of(999));
        CardPickPlayerAction invalidTopAction = new CardPickPlayerAction(invalidTopIDs, new HashSet<>());
        invalidTopAction.setPlayer(currPlayer);
        String[] errorsInvalidTop = roundActionState.validate(invalidTopAction);

        assertTrue(List.of(errorsInvalidTop).contains("Invalid top row card ID(s)"));


        Set<Integer> invalidBottomIDs = new HashSet<>(List.of(888));
        CardPickPlayerAction invalidBottomAction = new CardPickPlayerAction(new HashSet<>(), invalidBottomIDs);
        invalidBottomAction.setPlayer(currPlayer);
        String[] errorsInvalidBottom = roundActionState.validate(invalidBottomAction);

        assertTrue(List.of(errorsInvalidBottom).contains("Invalid bottom row card ID(s)"));



        currPlayer.setFood(0);
        Set<Integer> buildings = new HashSet<>();
        buildings.add(game.getBoard().getTopRow().getBuildingCards().get(0).getID());

        CardPickPlayerAction expensiveAction = new CardPickPlayerAction(buildings, new HashSet<>());
        expensiveAction.setPlayer(currPlayer);
        String[] errorsFood = roundActionState.validate(expensiveAction);
        assertTrue(List.of(errorsFood).contains("Not enough food for the building(s)"));


        CardPickPlayerAction validAction = new CardPickPlayerAction(new HashSet<>(), new HashSet<>());
        validAction.setPlayer(currPlayer);
        String[] noErrors = roundActionState.validate(validAction);

        assertEquals(0, noErrors.length);
    }


    @Test
    void fixReferencesTest() {
        String targetName = "Ciccio";

        Player detachedPlayer = new Player(targetName, Totem.BLACK);
        roundActionState.setCurrPlayer(detachedPlayer);

        Player officialPlayer = game.getPlayers().stream()
                .filter(p -> p.getName().equals(targetName))
                .findFirst()
                .orElseThrow();

        assertNotSame(officialPlayer, roundActionState.getCurrPlayer());

        roundActionState.fixReferences(game);

        assertSame(officialPlayer, roundActionState.getCurrPlayer());
    }
}