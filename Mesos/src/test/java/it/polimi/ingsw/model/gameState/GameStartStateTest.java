package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.BuildingHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerConfig;

import it.polimi.ingsw.model.player.Totem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameStartStateTest {
    private GameStartState gameStartState;
    private List<Player> players;
    private Game game;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        game = new Game(PlayerConfig.TWO, players);
        gameStartState = new GameStartState(game, new BuildingHandler());
        game.setGameState(gameStartState);
    }

    @Test
    void updateTest() {
        gameStartState.update();
        assertEquals(RoundStartState.class, game.getGameState().getClass());
    }

    @Test
    void assignPlayersToOrderTileTest() {
        for(OrderSlot os : game.getBoard().getOrderTile()) {
            assertNotNull(os.getAssignedPlayer());
        }
    }

    @Test
    void distributeCardsTest() {
        assertEquals(6, game.getBoard().getTopRow().getCharacterCards().size() + game.getBoard().getTopRow().getEventCards().size() + game.getBoard().getTopRow().getSustenanceEventCards().size());
        assertEquals(3, game.getBoard().getBottomRow().getCharacterCards().size());
        assertEquals(1, game.getBoard().getTopRow().getBuildingCards().size());
        assertEquals(0, game.getBoard().getBottomRow().getBuildingCards().size());
    }
}
