package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.client.action.CardPickPlayerAction;
import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.card.building.ExtraActionBuilding;
import it.polimi.ingsw.model.card.character.Hunter;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.player.Tribe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExtraActionStateTest {
    ExtraActionState extraActionState;
    private Game g;
    private List<Player> players;
    private BuildingHandler buildingHandler;



    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        players.get(0).setTribe(new Tribe());
        players.get(1).setTribe(new Tribe());
        g = new Game(PlayerConfig.TWO, players);
        extraActionState = new ExtraActionState(g, new BuildingHandler());
        g.setGameState(extraActionState);
    }
    @Test
    void updateTest () {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        players.add(new Player("Alice", Totem.WHITE));
        players.add(new Player("Bob", Totem.WHITE));

        g = new Game(PlayerConfig.FOUR, players);

        g.setLobbyState(new LobbyRunningState(new LobbyController(1, 4)));

        buildingHandler = new BuildingHandler();
        extraActionState = new ExtraActionState(g, buildingHandler);


        ExtraActionBuilding extraActionBuilding = new ExtraActionBuilding(1, false, 0, 0);
        extraActionBuilding.onPick(players.get(0), buildingHandler);

        g.setGameState(extraActionState);

        extraActionState.update();
        assertEquals(players.get(0), extraActionState.getCurrPlayer());
        assertEquals(ExtraActionState.class, g.getGameState().getClass());
        extraActionState.update();
        assertEquals(RoundStartState.class, g.getGameState().getClass());
    }


    @Test
    void validateTest() {
        Player currPlayer = players.get(0);
        Player wrongPlayer = players.get(1);
        extraActionState.setCurrPlayer(currPlayer);

        Set<Integer> validTopPicks = new HashSet<>();
        validTopPicks.add(g.getBoard().getTopRow().getCharacterCards().get(0).getID());


        CardPickPlayerAction wrongTurnAction = new CardPickPlayerAction(new HashSet<>(), new HashSet<>());
        wrongTurnAction.setPlayer(wrongPlayer);
        String[] errorsTurn = extraActionState.validate(wrongTurnAction);
        assertEquals("Actions are only allow during your turn", errorsTurn[0]);

        // --- wrong pick count ---
        Set<Integer> tooManyPicks = new HashSet<>();
        tooManyPicks.add(1);
        tooManyPicks.add(2);
        CardPickPlayerAction wrongPickCountAction = new CardPickPlayerAction(tooManyPicks, new HashSet<>());
        wrongPickCountAction.setPlayer(currPlayer);
        String[] errorsPickCount = extraActionState.validate(wrongPickCountAction);
        assertEquals("Invalid number of picks (required picks: 1 from top row", errorsPickCount[0]);

        // --- wrong id  ---
        Set<Integer> invalidTopPicks = new HashSet<>();
        invalidTopPicks.add(999); // ID non presente
        CardPickPlayerAction invalidIDAction = new CardPickPlayerAction(invalidTopPicks, new HashSet<>());
        invalidIDAction.setPlayer(currPlayer);
        String[] errorsInvalidID = extraActionState.validate(invalidIDAction);
        assertEquals("Invalid top row card ID(s)", errorsInvalidID[0]);


        // --- not enough food ---
        Set<Integer> expensivePicks = new HashSet<>();
        expensivePicks.add(g.getBoard().getTopRow().getBuildingCards().get(0).getID());
        CardPickPlayerAction expensiveAction = new CardPickPlayerAction(expensivePicks, new HashSet<>());
        expensiveAction.setPlayer(currPlayer);
        currPlayer.setFood(0);
        String[] errorsFoodCost = extraActionState.validate(expensiveAction);
        assertEquals("Not enough food for the building(s)", errorsFoodCost[0]);

        // --- valid pick ---
        CardPickPlayerAction validAction = new CardPickPlayerAction(validTopPicks, new HashSet<>());
        validAction.setPlayer(currPlayer);
        String[] noErrors = extraActionState.validate(validAction);
        assertEquals(0, noErrors.length);
    }


    @Test
    void copyTest() {
        Player currPlayer = players.get(0);
        extraActionState.setCurrPlayer(currPlayer);

        GameState copiedState = extraActionState.copy();

        assertInstanceOf(ExtraActionState.class, copiedState);

        ExtraActionState castedCopy = (ExtraActionState) copiedState;
        assertEquals(extraActionState.getCurrPlayer(), castedCopy.getCurrPlayer());
    }


    @Test
    void fixReferencesTest() {
        String targetName = "Ciccio";

        Player detachedPlayer = new Player(targetName, Totem.BLACK);
        extraActionState.setCurrPlayer(detachedPlayer);

        Player officialPlayer = g.getPlayers().stream()
                .filter(p -> p.getName().equals(targetName))
                .findFirst()
                .orElseThrow();

        assertNotSame(officialPlayer, extraActionState.getCurrPlayer());

        extraActionState.fixReferences(g);

        assertSame(officialPlayer, extraActionState.getCurrPlayer());
    }
}