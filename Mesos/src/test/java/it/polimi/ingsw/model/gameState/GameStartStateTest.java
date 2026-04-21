package it.polimi.ingsw.model.gameState;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.OrderSlot;
import it.polimi.ingsw.model.card.building.BuildingHandler;
import it.polimi.ingsw.model.gameState.GameStartState;
import it.polimi.ingsw.model.gameState.RoundStartState;
import it.polimi.ingsw.model.player.PlayerConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStartStateTest {
    private GameStartState gameStartState;
    private Game game;

    @BeforeEach
        void setUp(){
            game = new Game(PlayerConfig.TWO);
            gameStartState = new GameStartState(game, new BuildingHandler());
        }

    @Test
    void updateTest(){
        gameStartState.update();
        assertFalse( game.getGameState() instanceof RoundStartState);
        gameStartState.update();
        assertInstanceOf(RoundStartState.class,game.getGameState());
    }

    @Test
    void assignPlayersToOrderTileTest(){
        gameStartState.assignPlayersToOrderTile();
        OrderSlot[] orderSlots = game.getBoard().getOrderTile();
        for(OrderSlot slot : orderSlots){
            assertNotNull(slot.getAssignedPlayer());
        }
    }
}
