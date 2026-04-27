package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OfferTile;
import it.polimi.ingsw.model.card.building.BuildingHandler;
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

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Ciccio", Totem.BLACK));
        players.add(new Player("Gigio", Totem.WHITE));
        game = new Game(PlayerConfig.TWO, players);
        roundStartState = new RoundStartState(game, new BuildingHandler());
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
}