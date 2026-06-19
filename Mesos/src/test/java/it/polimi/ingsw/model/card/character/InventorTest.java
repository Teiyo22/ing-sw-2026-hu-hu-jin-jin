package it.polimi.ingsw.model.card.character;

import it.polimi.ingsw.model.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventorTest {
    private Player player;
    private Inventor inventor;

    @BeforeEach
    void setUp() {
        player = new Player("X", Totem.BLACK);
        player.setTribe(new Tribe());
        inventor = new Inventor("Inventor", 1, false, InventorType.BAKER);
    }

    @Test
    void testAddToTribeOf() {
        inventor.addToTribeOf(player);
        assertEquals(1, player.getTribe().getInventorCount());
    }
}
