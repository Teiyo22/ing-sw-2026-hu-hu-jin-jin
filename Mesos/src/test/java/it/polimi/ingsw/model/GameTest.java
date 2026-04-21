package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.PlayerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.ingsw.model.gameState.*;
import it.polimi.ingsw.model.player.Totem;
import it.polimi.ingsw.model.card.building.*;

public class GameTest {
    private Game g;

    @BeforeEach
    void setUp() {
        g= new Game(PlayerConfig.TWO);
    }

    @Test
    void setGameStateTest() {
        BuildingHandler buildingHandler= new BuildingHandler();
        GameState state= new GameStartState(g, buildingHandler);
        g.setGameState(state);
        assertEquals(GameStartState.class, g.getGameState().getClass());
    }

    @Test
    void addPlayerTest() {
        g.addPlayer(Totem.BLUE, "Gigio");
        assertEquals(1,g.getPlayers().size());
    }
}

