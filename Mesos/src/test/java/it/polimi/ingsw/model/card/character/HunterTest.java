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
        Hunter hunter = new Hunter(1, false, true);
        hunter.addToTribeOf(player);
        assertEquals(0, player.getFood());
        assertEquals(1, player.getTribe().getHunterCount());


        Hunter hunter2 = new Hunter(2, false, true);
        hunter2.addToTribeOf(player);
        assertEquals(1, player.getFood());
        assertEquals(2, player.getTribe().getHunterCount());


        Hunter hunter3 = new Hunter(3, false, false);
        hunter3.addToTribeOf(player);
        assertEquals(1, player.getFood());
        assertEquals(3, player.getTribe().getHunterCount());

    }
}
