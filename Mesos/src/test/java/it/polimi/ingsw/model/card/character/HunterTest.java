package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HunterTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("X", Totem.BLACK);
        player.setTribe(new Tribe());
    }

    @Test
    void testAddToTribeOf() {
        Hunter hunter = new Hunter("Hunter", 1, false, true);
        hunter.addToTribeOf(player);
        assertEquals(1, player.getTribe().getHunterCount());
        assertEquals(0, player.getFood()); //era 1

        Hunter hunter2 = new Hunter("Hunter", 2, false, true);
        hunter2.addToTribeOf(player);
        assertEquals(2, player.getTribe().getHunterCount());
        assertEquals(1, player.getFood()); //era 3

        Hunter hunter3 = new Hunter("Hunter", 3, false, false);
        hunter3.addToTribeOf(player);
        assertEquals(3, player.getTribe().getHunterCount());
        assertEquals(1, player.getFood());
    }
}
