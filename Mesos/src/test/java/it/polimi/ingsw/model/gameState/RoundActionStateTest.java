package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.controller.server.lobby.LobbyController;
import it.polimi.ingsw.controller.server.lobby.states.LobbyRunningState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;
import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
}