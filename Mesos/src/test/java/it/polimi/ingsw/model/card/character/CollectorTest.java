package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectorTest {
    private Collector collector;
    private Player player;

    @BeforeEach
    void setUp() {
        collector = new Collector("Collector", 1, false);
        player = new Player("X", Totem.BLACK);
    }

    @Test
    void testAddToTribeOf() {
        collector.addToTribeOf(player);
        assertEquals(1, player.getTribe().getCollectorCount());
    }

}